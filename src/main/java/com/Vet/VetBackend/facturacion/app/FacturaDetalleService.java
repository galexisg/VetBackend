package com.Vet.VetBackend.facturacion.app;

import com.Vet.VetBackend.facturacion.domain.Factura;
import com.Vet.VetBackend.facturacion.domain.FacturaDetalle;
import com.Vet.VetBackend.facturacion.repo.FacturaRepository;
import com.Vet.VetBackend.facturacion.repo.FacturaDetalleRepository;
import com.Vet.VetBackend.facturacion.web.dto.FacturaDetalleDTO;

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
public class FacturaDetalleService {

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    // ========== OPERACIONES CRUD ==========

    /**
     * Agregar detalle a una factura
     */
    public FacturaDetalleDTO agregarDetalle(Long facturaId, Long servicioId, Integer cantidad,
                                            BigDecimal precioUnitario, BigDecimal descuento) {
        // Validar factura
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        if ("ANULADA".equals(factura.getEstado())) {
            throw new IllegalArgumentException("No se puede modificar una factura anulada");
        }

        // Crear detalle
        FacturaDetalle detalle = new FacturaDetalle(factura, servicioId, cantidad,
                precioUnitario, descuento);

        if (!detalle.isValid()) {
            throw new IllegalArgumentException("Los datos del detalle son inválidos");
        }

        detalle = facturaDetalleRepository.save(detalle);

        return convertirADTO(detalle);
    }

    /**
     * Obtener detalles por factura
     */
    @Transactional(readOnly = true)
    public List<FacturaDetalleDTO> obtenerDetallesPorFactura(Long facturaId) {
        return facturaDetalleRepository.findByFacturaIdOrderById(facturaId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener detalle por ID
     */
    @Transactional(readOnly = true)
    public Optional<FacturaDetalleDTO> obtenerDetallePorId(Long detalleId) {
        return facturaDetalleRepository.findById(detalleId)
                .map(this::convertirADTO);
    }

    /**
     * Actualizar detalle
     */
    public FacturaDetalleDTO actualizarDetalle(Long detalleId, Integer nuevaCantidad,
                                               BigDecimal nuevoPrecio, BigDecimal nuevoDescuento) {
        FacturaDetalle detalle = facturaDetalleRepository.findById(detalleId)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado"));

        // Validar que la factura no esté anulada
        if ("ANULADA".equals(detalle.getFactura().getEstado())) {
            throw new IllegalArgumentException("No se puede modificar un detalle de factura anulada");
        }

        // Actualizar campos
        if (nuevaCantidad != null) {
            detalle.setCantidad(nuevaCantidad);
        }
        if (nuevoPrecio != null) {
            detalle.setPrecioUnitario(nuevoPrecio);
        }
        if (nuevoDescuento != null) {
            detalle.setDescuento(nuevoDescuento);
        }

        // Validar después de actualizar
        if (!detalle.isValid()) {
            throw new IllegalArgumentException("Los nuevos datos del detalle son inválidos");
        }

        detalle = facturaDetalleRepository.save(detalle);

        return convertirADTO(detalle);
    }

    /**
     * Eliminar detalle
     */
    public void eliminarDetalle(Long detalleId) {
        FacturaDetalle detalle = facturaDetalleRepository.findById(detalleId)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado"));

        // Validar que la factura no esté anulada
        if ("ANULADA".equals(detalle.getFactura().getEstado())) {
            throw new IllegalArgumentException("No se puede eliminar un detalle de factura anulada");
        }

        // Validar que no sea el único detalle
        long cantidadDetalles = facturaDetalleRepository.countByServicioId(detalle.getServicioId());
        if (cantidadDetalles <= 1) {
            throw new IllegalArgumentException("La factura debe tener al menos un detalle");
        }

        facturaDetalleRepository.delete(detalle);
    }

    // ========== OPERACIONES DE CONSULTA ==========

    /**
     * Calcular total de detalles por factura
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalDetalles(Long facturaId) {
        BigDecimal total = facturaDetalleRepository.getTotalSubtotalesPorFactura(facturaId);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Obtener servicios más vendidos
     */
    @Transactional(readOnly = true)
    public List<ServicioVendido> obtenerServiciosMasVendidos() {
        List<Object[]> resultados = facturaDetalleRepository.getServiciosMasVendidos();
        return resultados.stream()
                .map(obj -> new ServicioVendido((Long) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }

    /**
     * Obtener estadísticas de ventas por servicio
     */
    @Transactional(readOnly = true)
    public EstadisticaVentas obtenerEstadisticasServicio(Long servicioId,
                                                         LocalDateTime fechaInicio,
                                                         LocalDateTime fechaFin) {
        Object[] resultado = facturaDetalleRepository.getVentasServicioEnPeriodo(
                servicioId, fechaInicio, fechaFin);

        if (resultado != null && resultado.length == 2) {
            Long cantidadVendida = (Long) resultado[0];
            BigDecimal totalVendido = (BigDecimal) resultado[1];

            return new EstadisticaVentas(servicioId, cantidadVendida != null ? cantidadVendida : 0L,
                    totalVendido != null ? totalVendido : BigDecimal.ZERO,
                    fechaInicio, fechaFin);
        }

        return new EstadisticaVentas(servicioId, 0L, BigDecimal.ZERO, fechaInicio, fechaFin);
    }

    /**
     * Obtener detalles con descuento
     */
    @Transactional(readOnly = true)
    public List<FacturaDetalleDTO> obtenerDetallesConDescuento(BigDecimal descuentoMinimo) {
        return facturaDetalleRepository.findByDescuentoGreaterThan(descuentoMinimo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Validar consistencia de totales
     */
    @Transactional(readOnly = true)
    public boolean validarTotalesFactura(Long facturaId) {
        BigDecimal totalCalculado = calcularTotalDetalles(facturaId);

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        return totalCalculado.compareTo(factura.getTotal()) == 0;
    }

    // ========== OPERACIONES MASIVAS ==========

    /**
     * Aplicar descuento a todos los detalles de una factura
     */
    public List<FacturaDetalleDTO> aplicarDescuentoMasivo(Long facturaId, BigDecimal porcentajeDescuento) {
        List<FacturaDetalle> detalles = facturaDetalleRepository.findByFacturaId(facturaId);

        for (FacturaDetalle detalle : detalles) {
            BigDecimal montoBase = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            BigDecimal descuento = montoBase.multiply(porcentajeDescuento)
                    .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);

            detalle.setDescuento(descuento);
            facturaDetalleRepository.save(detalle);
        }

        return detalles.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ========== MÉTODOS PRIVADOS ==========

    private FacturaDetalleDTO convertirADTO(FacturaDetalle detalle) {
        FacturaDetalleDTO dto = new FacturaDetalleDTO();
        dto.setId(detalle.getId());
        dto.setFacturaId(detalle.getFactura().getId());
        dto.setServicioId(detalle.getServicioId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setDescuento(detalle.getDescuento());
        dto.setSubtotal(detalle.getSubtotal());

        // Calcular información adicional
        dto.calcularSubtotal();

        return dto;
    }

    // ========== CLASES INTERNAS ==========

    public static class ServicioVendido {
        private Long servicioId;
        private Long cantidadVendida;

        public ServicioVendido(Long servicioId, Long cantidadVendida) {
            this.servicioId = servicioId;
            this.cantidadVendida = cantidadVendida;
        }

        // Getters
        public Long getServicioId() { return servicioId; }
        public Long getCantidadVendida() { return cantidadVendida; }
    }

    public static class EstadisticaVentas {
        private Long servicioId;
        private Long cantidadVendida;
        private BigDecimal totalVendido;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaFin;

        public EstadisticaVentas(Long servicioId, Long cantidadVendida, BigDecimal totalVendido,
                                 LocalDateTime fechaInicio, LocalDateTime fechaFin) {
            this.servicioId = servicioId;
            this.cantidadVendida = cantidadVendida;
            this.totalVendido = totalVendido;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

        // Getters
        public Long getServicioId() { return servicioId; }
        public Long getCantidadVendida() { return cantidadVendida; }
        public BigDecimal getTotalVendido() { return totalVendido; }
        public LocalDateTime getFechaInicio() { return fechaInicio; }
        public LocalDateTime getFechaFin() { return fechaFin; }
    }
}