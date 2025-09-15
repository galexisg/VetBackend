package com.Vet.VetBackend.facturacion.app;

import com.Vet.VetBackend.facturacion.domain.Factura;
import com.Vet.VetBackend.facturacion.domain.FacturaDetalle;
import com.Vet.VetBackend.facturacion.domain.Pago;
import com.Vet.VetBackend.facturacion.repo.FacturaRepository;
import com.Vet.VetBackend.facturacion.repo.FacturaDetalleRepository;
import com.Vet.VetBackend.facturacion.repo.PagoRepository;
import com.Vet.VetBackend.facturacion.web.dto.FacturaDTO;
import com.Vet.VetBackend.facturacion.web.dto.FacturaRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private FacturaDetalleService facturaDetalleService;

    @Autowired
    private PagoService pagoService;

    // ========== OPERACIONES CRUD ==========

    /**
     * Crear una nueva factura con sus detalles
     */
    public FacturaDTO crearFactura(FacturaRequestDTO request) {
        // Validar request
        if (!request.isValid()) {
            throw new IllegalArgumentException("Los datos de la factura son inválidos");
        }

        // Verificar que no exista factura para la cita
        if (facturaRepository.findByCitaId(request.getCitaId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una factura para esta cita");
        }

        // Crear factura
        Factura factura = new Factura(
                request.getClienteId(),
                request.getCitaId(),
                request.getEstado() != null ? request.getEstado() : "PENDIENTE",
                BigDecimal.ZERO // Se calculará después
        );

        // Guardar factura para obtener ID
        factura = facturaRepository.save(factura);

        // Crear detalles
        BigDecimal totalFactura = BigDecimal.ZERO;
        for (FacturaRequestDTO.DetalleRequestDTO detalleRequest : request.getDetalles()) {
            FacturaDetalle detalle = new FacturaDetalle(
                    factura,
                    detalleRequest.getServicioId(),
                    detalleRequest.getCantidad(),
                    detalleRequest.getPrecioUnitario(),
                    detalleRequest.getDescuento()
            );
            facturaDetalleRepository.save(detalle);
            totalFactura = totalFactura.add(detalle.getSubtotal());
        }

        // Actualizar total y saldo de la factura
        factura.setTotal(totalFactura);
        factura.setSaldo(totalFactura);
        factura = facturaRepository.save(factura);

        // Procesar pago inicial si existe
        if (request.tienePagoInicial()) {
            pagoService.registrarPago(factura.getId(), request.getPagoInicial().getMetodo(),
                    request.getPagoInicial().getMonto());
        }

        return convertirADTO(factura);
    }

    /**
     * Obtener factura por ID
     */
    @Transactional(readOnly = true)
    public Optional<FacturaDTO> obtenerFacturaPorId(Long id) {
        return facturaRepository.findByIdComplete(id)
                .map(this::convertirADTO);
    }

    /**
     * Obtener facturas por cliente
     */
    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPorCliente(Long clienteId) {
        return facturaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener facturas por estado
     */
    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPorEstado(String estado) {
        return facturaRepository.findByEstado(estado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener facturas pendientes
     */
    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPendientes() {
        return facturaRepository.findFacturasPendientesPago()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar estado de factura
     */
    public FacturaDTO actualizarEstado(Long facturaId, String nuevoEstado) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        // Validar transición de estado
        if (!esTransicionValida(factura.getEstado(), nuevoEstado)) {
            throw new IllegalArgumentException("Transición de estado inválida: " +
                    factura.getEstado() + " -> " + nuevoEstado);
        }

        factura.setEstado(nuevoEstado);
        factura = facturaRepository.save(factura);

        return convertirADTO(factura);
    }

    /**
     * Anular factura
     */
    public FacturaDTO anularFactura(Long facturaId, String motivo, Long usuarioId) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        // Validar que se puede anular
        if ("ANULADA".equals(factura.getEstado())) {
            throw new IllegalArgumentException("La factura ya está anulada");
        }

        if (factura.getSaldo().compareTo(factura.getTotal()) < 0) {
            throw new IllegalArgumentException("No se puede anular una factura con pagos realizados");
        }

        // Anular factura
        factura.setEstado("ANULADA");
        factura.setMotivoAnulacion(motivo);
        factura.setUsuarioAnulaId(usuarioId);
        factura.setSaldo(BigDecimal.ZERO);

        factura = facturaRepository.save(factura);

        return convertirADTO(factura);
    }

    // ========== OPERACIONES DE NEGOCIO ==========

    /**
     * Recalcular totales de una factura
     */
    public FacturaDTO recalcularTotales(Long facturaId) {
        Factura factura = facturaRepository.findByIdWithDetalles(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        // Calcular nuevo total
        BigDecimal nuevoTotal = factura.getDetalles().stream()
                .map(FacturaDetalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        factura.setTotal(nuevoTotal);

        // Recalcular saldo
        BigDecimal totalPagado = pagoRepository.getTotalPagadoPorFactura(facturaId);
        if (totalPagado == null) totalPagado = BigDecimal.ZERO;

        factura.setSaldo(nuevoTotal.subtract(totalPagado));

        // Actualizar estado según saldo
        if (factura.getSaldo().compareTo(BigDecimal.ZERO) == 0) {
            factura.setEstado("PAGADA");
        } else if (factura.getSaldo().compareTo(factura.getTotal()) == 0) {
            factura.setEstado("PENDIENTE");
        } else {
            factura.setEstado("PARCIAL");
        }

        factura = facturaRepository.save(factura);

        return convertirADTO(factura);
    }

    /**
     * Obtener resumen financiero de facturas
     */
    @Transactional(readOnly = true)
    public ResumenFinanciero obtenerResumenFinanciero(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        BigDecimal totalFacturado = facturaRepository.getTotalFacturadoEnPeriodo(fechaInicio, fechaFin);
        if (totalFacturado == null) totalFacturado = BigDecimal.ZERO;

        BigDecimal totalRecaudado = pagoRepository.getTotalRecaudadoEnPeriodo(fechaInicio, fechaFin);
        if (totalRecaudado == null) totalRecaudado = BigDecimal.ZERO;

        List<Factura> facturasPendientes = facturaRepository.findFacturasPendientesPago();
        BigDecimal saldoPendiente = facturasPendientes.stream()
                .map(Factura::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long cantidadFacturas = facturaRepository.countByEstado("PENDIENTE") +
                facturaRepository.countByEstado("PAGADA") +
                facturaRepository.countByEstado("PARCIAL");

        return new ResumenFinanciero(totalFacturado, totalRecaudado, saldoPendiente,
                cantidadFacturas, facturasPendientes.size());
    }

    // ========== MÉTODOS PRIVADOS ==========

    private boolean esTransicionValida(String estadoActual, String nuevoEstado) {
        // Lógica de validación de transiciones de estado
        switch (estadoActual) {
            case "PENDIENTE":
                return "PAGADA".equals(nuevoEstado) || "PARCIAL".equals(nuevoEstado) || "ANULADA".equals(nuevoEstado);
            case "PARCIAL":
                return "PAGADA".equals(nuevoEstado) || "ANULADA".equals(nuevoEstado);
            case "PAGADA":
                return "ANULADA".equals(nuevoEstado); // Solo se puede anular
            case "ANULADA":
                return false; // No se puede cambiar desde anulada
            default:
                return false;
        }
    }

    private FacturaDTO convertirADTO(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setClienteId(factura.getClienteId());
        dto.setCitaId(factura.getCitaId());
        dto.setEstado(factura.getEstado());
        dto.setTotal(factura.getTotal());
        dto.setSaldo(factura.getSaldo());
        dto.setMotivoAnulacion(factura.getMotivoAnulacion());
        dto.setUsuarioAnulaId(factura.getUsuarioAnulaId());
        dto.setCreatedAt(factura.getCreatedAt());
        dto.setUpdatedAt(factura.getUpdatedAt());

        // Información adicional
        BigDecimal totalPagado = pagoRepository.getTotalPagadoPorFactura(factura.getId());
        dto.setTotalPagado(totalPagado != null ? totalPagado : BigDecimal.ZERO);

        if (factura.getDetalles() != null) {
            dto.setCantidadDetalles(factura.getDetalles().size());
        }

        if (factura.getPagos() != null) {
            dto.setCantidadPagos(factura.getPagos().size());
        }

        return dto;
    }

    // ========== CLASE INTERNA PARA RESUMEN ==========

    public static class ResumenFinanciero {
        private BigDecimal totalFacturado;
        private BigDecimal totalRecaudado;
        private BigDecimal saldoPendiente;
        private Long cantidadFacturas;
        private Integer facturasPendientes;

        public ResumenFinanciero(BigDecimal totalFacturado, BigDecimal totalRecaudado,
                                 BigDecimal saldoPendiente, Long cantidadFacturas, Integer facturasPendientes) {
            this.totalFacturado = totalFacturado;
            this.totalRecaudado = totalRecaudado;
            this.saldoPendiente = saldoPendiente;
            this.cantidadFacturas = cantidadFacturas;
            this.facturasPendientes = facturasPendientes;
        }

        // Getters
        public BigDecimal getTotalFacturado() { return totalFacturado; }
        public BigDecimal getTotalRecaudado() { return totalRecaudado; }
        public BigDecimal getSaldoPendiente() { return saldoPendiente; }
        public Long getCantidadFacturas() { return cantidadFacturas; }
        public Integer getFacturasPendientes() { return facturasPendientes; }
    }
}