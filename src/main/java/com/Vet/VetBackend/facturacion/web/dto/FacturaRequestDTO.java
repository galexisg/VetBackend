package com.Vet.VetBackend.facturacion.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturaRequestDTO {

    private Long clienteId;
    private Long citaId;
    private String estado;
    private String observaciones;

    // Lista de ítems a facturar (servicios y/o tratamientos)
    private List<DetalleRequestDTO> detalles;

    // Información de pago inicial (opcional)
    private PagoRequestDTO pagoInicial;

    // Constructor vacío
    public FacturaRequestDTO() {}

    // Constructor básico
    public FacturaRequestDTO(Long clienteId, Long citaId, List<DetalleRequestDTO> detalles) {
        this.clienteId = clienteId;
        this.citaId = citaId;
        this.estado = "PENDIENTE"; // default
        this.detalles = detalles;
    }

    // Getters y Setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public List<DetalleRequestDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRequestDTO> detalles) { this.detalles = detalles; }

    public PagoRequestDTO getPagoInicial() { return pagoInicial; }
    public void setPagoInicial(PagoRequestDTO pagoInicial) { this.pagoInicial = pagoInicial; }

    // Validación de la request completa
    public boolean isValid() {
        return clienteId != null && clienteId > 0
                && citaId != null && citaId > 0
                && detalles != null && !detalles.isEmpty()
                && detalles.stream().allMatch(DetalleRequestDTO::isValid);
    }

    public boolean tienePagoInicial() {
        return pagoInicial != null && pagoInicial.isValid();
    }

    // ================== DetalleRequestDTO ==================

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DetalleRequestDTO {
        // Yo mantengo compatibilidad: servicioId sigue existiendo
        private Long servicioId;

        // Yo agrego tratamientoId para el nuevo flujo
        private Long tratamientoId;        // NUEVO (nullable)

        // Yo agrego descripcionItem para “congelar” el texto mostrado (opcional)
        private String descripcionItem;    // NUEVO (nullable)

        private Integer cantidad;
        private java.math.BigDecimal precioUnitario;
        private java.math.BigDecimal descuento;

        // Constructor vacío
        public DetalleRequestDTO() {}

        // Constructor básico (servicio)
        public DetalleRequestDTO(Long servicioId, Integer cantidad, java.math.BigDecimal precioUnitario) {
            this.servicioId = servicioId;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.descuento = java.math.BigDecimal.ZERO;
        }

        // Constructor para tratamiento (nuevo)
        public DetalleRequestDTO(Integer cantidad, java.math.BigDecimal precioUnitario, Long tratamientoId) {
            this.tratamientoId = tratamientoId;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.descuento = java.math.BigDecimal.ZERO;
        }

        // Getters y Setters
        public Long getServicioId() { return servicioId; }
        public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

        public Long getTratamientoId() { return tratamientoId; }
        public void setTratamientoId(Long tratamientoId) { this.tratamientoId = tratamientoId; }

        public String getDescripcionItem() { return descripcionItem; }
        public void setDescripcionItem(String descripcionItem) { this.descripcionItem = descripcionItem; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

        public java.math.BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(java.math.BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

        public java.math.BigDecimal getDescuento() { return descuento; }
        public void setDescuento(java.math.BigDecimal descuento) { this.descuento = descuento; }

        // Validación del ítem:
        // Yo aplico XOR: exactamente uno entre servicioId y tratamientoId.
        public boolean isValid() {
            boolean xor = (servicioId != null && servicioId > 0) ^ (tratamientoId != null && tratamientoId > 0);
            boolean qtyOk = (cantidad != null && cantidad > 0);
            boolean priceOk = (precioUnitario != null && precioUnitario.compareTo(java.math.BigDecimal.ZERO) > 0);
            return xor && qtyOk && priceOk;
        }
    }

    // ================== PagoRequestDTO ==================

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PagoRequestDTO {
        private String metodo;
        private java.math.BigDecimal monto;
        private String referencia;
        private String observaciones;

        public PagoRequestDTO() {}

        public PagoRequestDTO(String metodo, java.math.BigDecimal monto) {
            this.metodo = metodo;
            this.monto = monto;
        }

        public String getMetodo() { return metodo; }
        public void setMetodo(String metodo) { this.metodo = metodo; }

        public java.math.BigDecimal getMonto() { return monto; }
        public void setMonto(java.math.BigDecimal monto) { this.monto = monto; }

        public String getReferencia() { return referencia; }
        public void setReferencia(String referencia) { this.referencia = referencia; }

        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

        public boolean isValid() {
            return metodo != null && !metodo.trim().isEmpty()
                    && monto != null && monto.compareTo(java.math.BigDecimal.ZERO) > 0;
        }
    }
}
