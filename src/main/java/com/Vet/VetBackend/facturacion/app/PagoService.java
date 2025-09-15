package com.Vet.VetBackend.facturacion.app;

import com.Vet.VetBackend.facturacion.domain.Factura;
import com.Vet.VetBackend.facturacion.domain.Pago;
import com.Vet.VetBackend.facturacion.repo.FacturaRepository;
import com.Vet.VetBackend.facturacion.repo.PagoRepository;
import com.Vet.VetBackend.facturacion.web.dto.PagoDTO;

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
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    // ========== OPERACIONES CRUD ==========

    /**
     * Registrar un nuevo pago
     */
    public PagoDTO registrarPago(Long facturaId, String metodo, BigDecimal monto) {
        return registrarPago(facturaId, metodo, monto, LocalDateTime.now());
    }

    /**
     * Registrar pago con fecha específica
     */
    public PagoDTO registrarPago(Long facturaId, String metodo, BigDecimal monto, LocalDateTime fechaPago) {
        // Validar factura
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        if ("ANULADA".equals(factura.getEstado())) {
            throw new IllegalArgumentException("No se pueden registrar pagos en facturas anuladas");
        }

        // Validar monto
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero");
        }

        if (monto.compareTo(factura.getSaldo()) > 0) {
            throw new IllegalArgumentException("El monto del pago no puede ser mayor al saldo pendiente");
        }

        // Validar método de pago
        if (!esMetodoValido(metodo)) {
            throw new IllegalArgumentException("Método de pago no válido");
        }

        // Crear pago
        Pago pago = new Pago(factura, metodo, monto, fechaPago);
        pago = pagoRepository.save(pago);

        // Actualizar saldo y estado de la factura
        actualizarFacturaDespuesPago(factura);

        return convertirADTO(pago);
    }

    /**
     * Obtener pagos por factura
     */
    @Transactional(readOnly = true)
    public List<PagoDTO> obtenerPagosPorFactura(Long facturaId) {
        return pagoRepository.findByFacturaIdOrderByFechaPagoDesc(facturaId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener pago por ID
     */
    @Transactional(readOnly = true)
    public Optional<PagoDTO> obtenerPagoPorId(Long pagoId) {
        return pagoRepository.findById(pagoId)
                .map(this::convertirADTO);
    }

    /**
     * Obtener pagos por método
     */
    @Transactional(readOnly = true)
    public List<PagoDTO> obtenerPagosPorMetodo(String metodo) {
        return pagoRepository.findByMetodo(metodo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener pagos en un rango de fechas
     */
    @Transactional(readOnly = true)
    public List<PagoDTO> obtenerPagosEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Anular pago (eliminar)
     */
    public void anularPago(Long pagoId, String motivo) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        Factura factura = pago.getFactura();

        if ("ANULADA".equals(factura.getEstado())) {
            throw new IllegalArgumentException("No se pueden anular pagos de facturas anuladas");
        }

        // Eliminar pago
        pagoRepository.delete(pago);

        // Actualizar factura
        actualizarFacturaDespuesPago(factura);
    }

    // ========== OPERACIONES DE CONSULTA Y ESTADÍSTICAS ==========

    /**
     * Obtener total pagado por factura
     */
    @Transactional(readOnly = true)
    public BigDecimal obtenerTotalPagado(Long facturaId) {
        BigDecimal total = pagoRepository.getTotalPagadoPorFactura(facturaId);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Obtener recaudación por método de pago
     */
    @Transactional(readOnly = true)
    public List<RecaudacionPorMetodo> obtenerRecaudacionPorMetodo() {
        List<Object[]> resultados = pagoRepository.getTotalRecaudadoPorMetodo();
        return resultados.stream()
                .map(obj -> new RecaudacionPorMetodo((String) obj[0], (BigDecimal) obj[1]))
                .collect(Collectors.toList());
    }

    /**
     * Obtener recaudación diaria
     */
    @Transactional(readOnly = true)
    public List<RecaudacionDiaria> obtenerRecaudacionDiaria(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Object[]> resultados = pagoRepository.getRecaudacionDiaria(fechaInicio, fechaFin);
        return resultados.stream()
                .map(obj -> new RecaudacionDiaria((java.sql.Date) obj[0], (BigDecimal) obj[1]))
                .collect(Collectors.toList());
    }

    /**
     * Obtener total recaudado en período
     */
    @Transactional(readOnly = true)
    public BigDecimal obtenerTotalRecaudado(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        BigDecimal total = pagoRepository.getTotalRecaudadoEnPeriodo(fechaInicio, fechaFin);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Obtener pagos de un cliente
     */
    @Transactional(readOnly = true)
    public List<PagoDTO> obtenerPagosPorCliente(Long clienteId) {
        return pagoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener último pago de una factura
     */
    @Transactional(readOnly = true)
    public Optional<PagoDTO> obtenerUltimoPago(Long facturaId) {
        Pago ultimoPago = pagoRepository.findUltimoPagoPorFactura(facturaId);
        return ultimoPago != null ? Optional.of(convertirADTO(ultimoPago)) : Optional.empty();
    }

    /**
     * Obtener estadísticas de pagos hoy
     */
    @Transactional(readOnly = true)
    public EstadisticasPagosHoy obtenerEstadisticasHoy() {
        List<Pago> pagosHoy = pagoRepository.findPagosHoy();

        BigDecimal totalRecaudado = pagosHoy.stream()
                .map(Pago::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int cantidadPagos = pagosHoy.size();

        BigDecimal promedioPago = cantidadPagos > 0 ?
                totalRecaudado.divide(BigDecimal.valueOf(cantidadPagos), 2, BigDecimal.ROUND_HALF_UP) :
                BigDecimal.ZERO;

        return new EstadisticasPagosHoy(totalRecaudado, cantidadPagos, promedioPago, LocalDateTime.now());
    }

    // ========== VALIDACIONES Y UTILIDADES ==========

    /**
     * Validar si se puede registrar un pago
     */
    @Transactional(readOnly = true)
    public boolean puedeRegistrarPago(Long facturaId, BigDecimal monto) {
        Optional<Factura> facturaOpt = facturaRepository.findById(facturaId);

        if (!facturaOpt.isPresent()) {
            return false;
        }

        Factura factura = facturaOpt.get();

        return !"ANULADA".equals(factura.getEstado()) &&
                monto != null &&
                monto.compareTo(BigDecimal.ZERO) > 0 &&
                monto.compareTo(factura.getSaldo()) <= 0;
    }

    /**
     * Obtener saldo pendiente después de un posible pago
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularSaldoDespuesPago(Long facturaId, BigDecimal montoPago) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        return factura.getSaldo().subtract(montoPago);
    }

    // ========== MÉTODOS PRIVADOS ==========

    private boolean esMetodoValido(String metodo) {
        if (metodo == null || metodo.trim().isEmpty()) {
            return false;
        }

        String metodoUpper = metodo.toUpperCase();
        return metodoUpper.equals("EFECTIVO") ||
                metodoUpper.equals("TARJETA") ||
                metodoUpper.equals("TRANSFERENCIA") ||
                metodoUpper.equals("CHEQUE");
    }

    private void actualizarFacturaDespuesPago(Factura factura) {
        // Recalcular saldo
        BigDecimal totalPagado = pagoRepository.getTotalPagadoPorFactura(factura.getId());
        if (totalPagado == null) totalPagado = BigDecimal.ZERO;

        BigDecimal nuevoSaldo = factura.getTotal().subtract(totalPagado);
        factura.setSaldo(nuevoSaldo);

        // Actualizar estado
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
            factura.setEstado("PAGADA");
        } else if (nuevoSaldo.compareTo(factura.getTotal()) == 0) {
            factura.setEstado("PENDIENTE");
        } else {
            factura.setEstado("PARCIAL");
        }

        facturaRepository.save(factura);
    }

    private PagoDTO convertirADTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setFacturaId(pago.getFactura().getId());
        dto.setMetodo(pago.getMetodo());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setCreatedAt(pago.getCreatedAt());

        // Información adicional
        dto.setMetodoDescripcion(dto.getMetodoFormateado());

        return dto;
    }

    // ========== CLASES INTERNAS ==========

    public static class RecaudacionPorMetodo {
        private String metodo;
        private BigDecimal totalRecaudado;

        public RecaudacionPorMetodo(String metodo, BigDecimal totalRecaudado) {
            this.metodo = metodo;
            this.totalRecaudado = totalRecaudado;
        }

        // Getters
        public String getMetodo() { return metodo; }
        public BigDecimal getTotalRecaudado() { return totalRecaudado; }
    }

    public static class RecaudacionDiaria {
        private java.sql.Date fecha;
        private BigDecimal totalRecaudado;

        public RecaudacionDiaria(java.sql.Date fecha, BigDecimal totalRecaudado) {
            this.fecha = fecha;
            this.totalRecaudado = totalRecaudado;
        }

        // Getters
        public java.sql.Date getFecha() { return fecha; }
        public BigDecimal getTotalRecaudado() { return totalRecaudado; }
    }

    public static class EstadisticasPagosHoy {
        private BigDecimal totalRecaudado;
        private Integer cantidadPagos;
        private BigDecimal promedioPago;
        private LocalDateTime fechaConsulta;

        public EstadisticasPagosHoy(BigDecimal totalRecaudado, Integer cantidadPagos,
                                    BigDecimal promedioPago, LocalDateTime fechaConsulta) {
            this.totalRecaudado = totalRecaudado;
            this.cantidadPagos = cantidadPagos;
            this.promedioPago = promedioPago;
            this.fechaConsulta = fechaConsulta;
        }

        // Getters
        public BigDecimal getTotalRecaudado() { return totalRecaudado; }
        public Integer getCantidadPagos() { return cantidadPagos; }
        public BigDecimal getPromedioPago() { return promedioPago; }
        public LocalDateTime getFechaConsulta() { return fechaConsulta; }
    }
}