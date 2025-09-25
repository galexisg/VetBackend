package com.Vet.VetBackend.facturacion.app;

import com.Vet.VetBackend.facturacion.domain.Factura;
import com.Vet.VetBackend.facturacion.domain.FacturaDetalle;
import com.Vet.VetBackend.facturacion.domain.FacturaEstado;
import com.Vet.VetBackend.facturacion.repo.FacturaDetalleRepository;
import com.Vet.VetBackend.facturacion.repo.FacturaRepository;
import com.Vet.VetBackend.facturacion.repo.PagoRepository;
import com.Vet.VetBackend.facturacion.web.dto.FacturaDTO;
import com.Vet.VetBackend.facturacion.web.dto.FacturaRequestDTO;
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

    private final FacturaRepository facturaRepository;
    private final FacturaDetalleRepository facturaDetalleRepository;
    private final PagoRepository pagoRepository;

    private final FacturaDetalleService facturaDetalleService;
    private final PagoService pagoService;

    public FacturaService(FacturaRepository facturaRepository,
                          FacturaDetalleRepository facturaDetalleRepository,
                          PagoRepository pagoRepository,
                          FacturaDetalleService facturaDetalleService,
                          PagoService pagoService) {
        this.facturaRepository = facturaRepository;
        this.facturaDetalleRepository = facturaDetalleRepository;
        this.pagoRepository = pagoRepository;
        this.facturaDetalleService = facturaDetalleService;
        this.pagoService = pagoService;
    }

    // ========== CRUD ==========

    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerTodasFacturas() {
        return facturaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public FacturaDTO crearFactura(FacturaRequestDTO request) {
        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("Los datos de la factura son inválidos");
        }

        if (request.getCitaId() != null && facturaRepository.findByCitaId(request.getCitaId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una factura para esta cita");
        }

        // yo convierto el String del DTO a enum; si viene null, uso PENDIENTE
        FacturaEstado estadoInicial = parseEstadoOrDefault(request.getEstado(), FacturaEstado.PENDIENTE);

        // creo la factura con estado enum y saldo 0 para no violar NOT NULL
        Factura factura = new Factura(
                request.getClienteId(),
                request.getCitaId(),
                estadoInicial,
                BigDecimal.ZERO
        );
        factura.setSaldo(BigDecimal.ZERO); // defensivo para el primer insert
        factura = facturaRepository.save(factura);

        BigDecimal totalFactura = BigDecimal.ZERO;

        if (request.getDetalles() != null && !request.getDetalles().isEmpty()) {
            for (FacturaRequestDTO.DetalleRequestDTO d : request.getDetalles()) {
                NormalizedDetalle nd = normalizeDetalleRequestCompat(d);

                if (nd.cantidad == null || nd.cantidad <= 0) {
                    throw new IllegalArgumentException("cantidad debe ser > 0");
                }
                if (nd.precioUnitario == null || nd.precioUnitario.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("precioUnitario debe ser > 0");
                }

                FacturaDetalle detalle = new FacturaDetalle(
                        factura,
                        nd.servicioId,      // hoy tu DTO solo trae servicioId
                        nd.tratamientoId,   // quedará null (XOR ok)
                        nd.cantidad,
                        nd.precioUnitario,
                        nd.descuento != null ? nd.descuento : BigDecimal.ZERO,
                        nd.descripcionItem  // null hoy
                );

                if (!detalle.isValid()) {
                    throw new IllegalArgumentException("Detalle inválido (verifique XOR, cantidad, precio_unitario).");
                }

                facturaDetalleRepository.save(detalle);
                totalFactura = totalFactura.add(detalle.getSubtotal());
            }
        }

        factura.setTotal(totalFactura);
        factura.setSaldo(totalFactura);
        factura = facturaRepository.save(factura);

        if (request.tienePagoInicial()) {
            pagoService.registrarPago(
                    factura.getId(),
                    request.getPagoInicial().getMetodo(),
                    request.getPagoInicial().getMonto()
            );
            return recalcularTotales(factura.getId());
        }

        return convertirADTO(factura);
    }

    @Transactional(readOnly = true)
    public Optional<FacturaDTO> obtenerFacturaPorId(Long id) {
        return facturaRepository.findByIdComplete(id).map(this::convertirADTO);
    }

    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPorCliente(Long clienteId) {
        return facturaRepository.findByClienteId(clienteId)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPorEstado(String estado) {
        FacturaEstado e = parseEstado(estado);
        return facturaRepository.findByEstado(e)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPendientes() {
        return facturaRepository.findFacturasPendientesPago()
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public FacturaDTO actualizarEstado(Long facturaId, String nuevoEstado) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        FacturaEstado nuevo = parseEstado(nuevoEstado);
        if (!esTransicionValida(factura.getEstado(), nuevo)) {
            throw new IllegalArgumentException("Transición de estado inválida: " +
                    factura.getEstado() + " -> " + nuevo);
        }

        factura.setEstado(nuevo);
        factura = facturaRepository.save(factura);
        return convertirADTO(factura);
    }

    public FacturaDTO anularFactura(Long facturaId, String motivo, Long usuarioId) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        if (factura.getEstado() == FacturaEstado.ANULADA) {
            throw new IllegalArgumentException("La factura ya está anulada");
        }
        if (factura.getSaldo().compareTo(factura.getTotal()) < 0) {
            throw new IllegalArgumentException("No se puede anular una factura con pagos realizados");
        }

        factura.setEstado(FacturaEstado.ANULADA);
        factura.setMotivoAnulacion(motivo);
        factura.setUsuarioAnulaId(usuarioId);
        factura.setSaldo(BigDecimal.ZERO);

        factura = facturaRepository.save(factura);
        return convertirADTO(factura);
    }

    // ========== NEGOCIO ==========

    public FacturaDTO recalcularTotales(Long facturaId) {
        Factura factura = facturaRepository.findByIdWithDetalles(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        BigDecimal nuevoTotal = factura.getDetalles().stream()
                .map(FacturaDetalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        factura.setTotal(nuevoTotal);

        BigDecimal totalPagado = pagoRepository.getTotalPagadoPorFactura(facturaId);
        if (totalPagado == null) totalPagado = BigDecimal.ZERO;

        factura.setSaldo(nuevoTotal.subtract(totalPagado));

        if (factura.getSaldo().compareTo(BigDecimal.ZERO) == 0) {
            factura.setEstado(FacturaEstado.PAGADA);
        } else if (factura.getSaldo().compareTo(factura.getTotal()) == 0) {
            factura.setEstado(FacturaEstado.PENDIENTE);
        } else {
            factura.setEstado(FacturaEstado.PARCIAL);
        }

        factura = facturaRepository.save(factura);
        return convertirADTO(factura);
    }

    @Transactional(readOnly = true)
    public ResumenFinanciero obtenerResumenFinanciero(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        BigDecimal totalFacturado = nvl(facturaRepository.getTotalFacturadoEnPeriodo(fechaInicio, fechaFin));
        BigDecimal totalRecaudado = nvl(pagoRepository.getTotalRecaudadoEnPeriodo(fechaInicio, fechaFin));

        List<Factura> facturasPendientes = facturaRepository.findFacturasPendientesPago();
        BigDecimal saldoPendiente = facturasPendientes.stream()
                .map(Factura::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long cantidadFacturas =
                facturaRepository.countByEstado(FacturaEstado.PENDIENTE) +
                        facturaRepository.countByEstado(FacturaEstado.PAGADA) +
                        facturaRepository.countByEstado(FacturaEstado.PARCIAL);

        return new ResumenFinanciero(totalFacturado, totalRecaudado, saldoPendiente,
                cantidadFacturas, facturasPendientes.size());
    }

    // ========== PRIVADOS ==========

    private boolean esTransicionValida(FacturaEstado actual, FacturaEstado nuevo) {
        switch (actual) {
            case PENDIENTE:
                return nuevo == FacturaEstado.PAGADA || nuevo == FacturaEstado.PARCIAL || nuevo == FacturaEstado.ANULADA;
            case PARCIAL:
                return nuevo == FacturaEstado.PAGADA || nuevo == FacturaEstado.ANULADA;
            case PAGADA:
                return nuevo == FacturaEstado.ANULADA;
            case ANULADA:
                return false;
            default:
                return false;
        }
    }

    private FacturaDTO convertirADTO(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setClienteId(factura.getClienteId());
        dto.setCitaId(factura.getCitaId());
        dto.setEstado(factura.getEstado() != null ? factura.getEstado().name() : null);
        dto.setTotal(factura.getTotal());
        dto.setSaldo(factura.getSaldo());
        dto.setMotivoAnulacion(factura.getMotivoAnulacion());
        dto.setUsuarioAnulaId(factura.getUsuarioAnulaId());
        dto.setCreatedAt(factura.getCreatedAt());
        dto.setUpdatedAt(factura.getUpdatedAt());

        BigDecimal totalPagado = pagoRepository.getTotalPagadoPorFactura(factura.getId());
        dto.setTotalPagado(totalPagado != null ? totalPagado : BigDecimal.ZERO);

        if (factura.getDetalles() != null) dto.setCantidadDetalles(factura.getDetalles().size());
        if (factura.getPagos() != null) dto.setCantidadPagos(factura.getPagos().size());

        return dto;
    }

    private BigDecimal nvl(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }

    private FacturaEstado parseEstado(String estado) {
        if (estado == null) throw new IllegalArgumentException("Estado requerido");
        try {
            return FacturaEstado.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Estado inválido: " + estado + " (valores: PENDIENTE, PARCIAL, PAGADA, ANULADA)");
        }
    }

    private FacturaEstado parseEstadoOrDefault(String estado, FacturaEstado def) {
        if (estado == null || estado.isBlank()) return def;
        return parseEstado(estado);
    }

    /**
     * Normalización compatible con tu DTO actual (solo servicioId).
     */
    private NormalizedDetalle normalizeDetalleRequestCompat(FacturaRequestDTO.DetalleRequestDTO d) {
        NormalizedDetalle nd = new NormalizedDetalle();
        nd.servicioId = d.getServicioId();
        nd.tratamientoId = null;
        nd.cantidad = d.getCantidad();
        nd.precioUnitario = d.getPrecioUnitario();
        nd.descuento = d.getDescuento();
        nd.descripcionItem = null;
        return nd;
    }

    private static class NormalizedDetalle {
        Long servicioId;
        Long tratamientoId;
        Integer cantidad;
        BigDecimal precioUnitario;
        BigDecimal descuento;
        String descripcionItem;
    }

    // ========== DTO interno ==========

    public static class ResumenFinanciero {
        private final BigDecimal totalFacturado;
        private final BigDecimal totalRecaudado;
        private final BigDecimal saldoPendiente;
        private final Long cantidadFacturas;
        private final Integer facturasPendientes;

        public ResumenFinanciero(BigDecimal totalFacturado, BigDecimal totalRecaudado,
                                 BigDecimal saldoPendiente, Long cantidadFacturas, Integer facturasPendientes) {
            this.totalFacturado = totalFacturado;
            this.totalRecaudado = totalRecaudado;
            this.saldoPendiente = saldoPendiente;
            this.cantidadFacturas = cantidadFacturas;
            this.facturasPendientes = facturasPendientes;
        }

        public BigDecimal getTotalFacturado() { return totalFacturado; }
        public BigDecimal getTotalRecaudado() { return totalRecaudado; }
        public BigDecimal getSaldoPendiente() { return saldoPendiente; }
        public Long getCantidadFacturas() { return cantidadFacturas; }
        public Integer getFacturasPendientes() { return facturasPendientes; }
    }
}
