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
import java.math.RoundingMode;
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
     * Agregar detalle de SERVICIO a una factura (flujo actual del controller).
     * Usa el constructor COMPLETO de la entidad con tratamientoId = null.
     */
    public FacturaDetalleDTO agregarDetalle(Long facturaId, Long servicioId, Integer cantidad,
                                            BigDecimal precioUnitario, BigDecimal descuento) {

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        if ("ANULADA".equals(factura.getEstado())) {
            throw new IllegalArgumentException("No se puede modificar una factura anulada");
        }
        if (servicioId == null || servicioId <= 0) {
            throw new IllegalArgumentException("servicioId inválido");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("cantidad debe ser > 0");
        }
        if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("precioUnitario debe ser > 0");
        }

        BigDecimal desc = (descuento != null) ? descuento : BigDecimal.ZERO;

        // Constructor completo: (factura, servicioId, tratamientoId, cantidad, precio, descuento, descripcionItem)
        FacturaDetalle detalle = new FacturaDetalle(
                factura,
                servicioId,
                null,                // tratamiento_id -> null (XOR)
                cantidad,
                precioUnitario,
                desc,
                null                 // descripcion_item opcional
        );

        if (!detalle.isValid()) {
            throw new IllegalArgumentException("Los datos del detalle son inválidos");
        }

        detalle = facturaDetalleRepository.save(detalle);
        return convertirADTO(detalle);
    }

    /**
     * Agregar detalle de TRATAMIENTO (por si luego expones un endpoint para esto).
     */
    public FacturaDetalleDTO agregarDetalleTratamiento(Long facturaId, Long tratamientoId, Integer cantidad,
                                                       BigDecimal precioUnitario, BigDecimal descuento,
                                                       String descripcionItem) {

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        if ("ANULADA".equals(factura.getEstado())) {
            throw new IllegalArgumentException("No se puede modificar una factura anulada");
        }
        if (tratamientoId == null || tratamientoId <= 0) {
            throw new IllegalArgumentException("tratamientoId inválido");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("cantidad debe ser > 0");
        }
        if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("precioUnitario debe ser > 0");
        }

        BigDecimal desc = (descuento != null) ? descuento : BigDecimal.ZERO;

        FacturaDetalle detalle = new FacturaDetalle(
                factura,
                null,               // servicio_id -> null (XOR)
                tratamientoId,
                cantidad,
                precioUnitario,
                desc,
                descripcionItem
        );

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
     * Actualizar detalle (cantidad/precio/descuento).
     */
    public FacturaDetalleDTO actualizarDetalle(Long detalleId, Integer nuevaCantidad,
                                               BigDecimal nuevoPrecio, BigDecimal nuevoDescuento) {
        FacturaDetalle detalle = facturaDetalleRepository.findById(detalleId)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado"));

        if ("ANULADA".equals(detalle.getFactura().getEstado())) {
            throw new IllegalArgumentException("No se puede modificar un detalle de factura anulada");
        }

        if (nuevaCantidad != null) detalle.setCantidad(nuevaCantidad);
        if (nuevoPrecio != null) detalle.setPrecioUnitario(nuevoPrecio);
        if (nuevoDescuento != null) detalle.setDescuento(nuevoDescuento);

        if (!detalle.isValid()) {
            throw new IllegalArgumentException("Los nuevos datos del detalle son inválidos");
        }

        detalle = facturaDetalleRepository.save(detalle);
        return convertirADTO(detalle);
    }

    /**
     * Eliminar detalle (impido borrar el único detalle de la factura).
     */
    public void eliminarDetalle(Long detalleId) {
        FacturaDetalle detalle = facturaDetalleRepository.findById(detalleId)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado"));

        if ("ANULADA".equals(detalle.getFactura().getEstado())) {
            throw new IllegalArgumentException("No se puede eliminar un detalle de factura anulada");
        }

        Long facturaId = detalle.getFactura().getId();

        // Yo cuento por factura, no por servicio, para soportar tratamientos también.
        long cantidadDetallesEnFactura = facturaDetalleRepository.countByFacturaId(facturaId);
        if (cantidadDetallesEnFactura <= 1) {
            throw new IllegalArgumentException("La factura debe tener al menos un detalle");
        }

        facturaDetalleRepository.delete(detalle);
    }

    // ========== OPERACIONES DE CONSULTA ==========

    @Transactional(readOnly = true)
    public BigDecimal calcularTotalDetalles(Long facturaId) {
        BigDecimal total = facturaDetalleRepository.getTotalSubtotalesPorFactura(facturaId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public List<ServicioVendido> obtenerServiciosMasVendidos() {
        List<Object[]> resultados = facturaDetalleRepository.getServiciosMasVendidos();
        return resultados.stream()
                .map(obj -> new ServicioVendido((Long) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadisticaVentas obtenerEstadisticasServicio(Long servicioId,
                                                         LocalDateTime fechaInicio,
                                                         LocalDateTime fechaFin) {
        Object[] resultado = facturaDetalleRepository.getVentasServicioEnPeriodo(
                servicioId, fechaInicio, fechaFin);

        if (resultado != null && resultado.length == 2) {
            Long cantidadVendida = (Long) resultado[0];
            BigDecimal totalVendido = (BigDecimal) resultado[1];

            return new EstadisticaVentas(
                    servicioId,
                    cantidadVendida != null ? cantidadVendida : 0L,
                    totalVendido != null ? totalVendido : BigDecimal.ZERO,
                    fechaInicio, fechaFin
            );
        }

        return new EstadisticaVentas(servicioId, 0L, BigDecimal.ZERO, fechaInicio, fechaFin);
    }

    @Transactional(readOnly = true)
    public List<FacturaDetalleDTO> obtenerDetallesConDescuento(BigDecimal descuentoMinimo) {
        return facturaDetalleRepository.findByDescuentoGreaterThan(descuentoMinimo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean validarTotalesFactura(Long facturaId) {
        BigDecimal totalCalculado = calcularTotalDetalles(facturaId);

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        return totalCalculado.compareTo(factura.getTotal()) == 0;
    }

    // ========== OPERACIONES MASIVAS ==========

    public List<FacturaDetalleDTO> aplicarDescuentoMasivo(Long facturaId, BigDecimal porcentajeDescuento) {
        List<FacturaDetalle> detalles = facturaDetalleRepository.findByFacturaId(facturaId);

        for (FacturaDetalle detalle : detalles) {
            BigDecimal base = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
            BigDecimal descuento = base.multiply(porcentajeDescuento)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP); // reemplazo constante deprecada

            detalle.setDescuento(descuento);
            facturaDetalleRepository.save(detalle);
        }

        return detalles.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // ========== PRIVADOS ==========

    private FacturaDetalleDTO convertirADTO(FacturaDetalle d) {
        FacturaDetalleDTO dto = new FacturaDetalleDTO();
        dto.setId(d.getId());
        dto.setFacturaId(d.getFactura().getId());
        dto.setServicioId(d.getServicioId()); // si es tratamiento, quedará null (ok para tu DTO actual)
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        dto.setDescuento(d.getDescuento());
        dto.setSubtotal(d.getSubtotal());
        dto.calcularSubtotal(); // si tu DTO lo necesita
        return dto;
    }

    // ========== CLASES INTERNAS ==========

    public static class ServicioVendido {
        private final Long servicioId;
        private final Long cantidadVendida;

        public ServicioVendido(Long servicioId, Long cantidadVendida) {
            this.servicioId = servicioId;
            this.cantidadVendida = cantidadVendida;
        }

        public Long getServicioId() { return servicioId; }
        public Long getCantidadVendida() { return cantidadVendida; }
    }

    public static class EstadisticaVentas {
        private final Long servicioId;
        private final Long cantidadVendida;
        private final BigDecimal totalVendido;
        private final LocalDateTime fechaInicio;
        private final LocalDateTime fechaFin;

        public EstadisticaVentas(Long servicioId, Long cantidadVendida, BigDecimal totalVendido,
                                 LocalDateTime fechaInicio, LocalDateTime fechaFin) {
            this.servicioId = servicioId;
            this.cantidadVendida = cantidadVendida;
            this.totalVendido = totalVendido;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

        public Long getServicioId() { return servicioId; }
        public Long getCantidadVendida() { return cantidadVendida; }
        public BigDecimal getTotalVendido() { return totalVendido; }
        public LocalDateTime getFechaInicio() { return fechaInicio; }
        public LocalDateTime getFechaFin() { return fechaFin; }
    }
}
