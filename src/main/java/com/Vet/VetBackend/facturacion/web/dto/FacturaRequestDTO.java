package com.Vet.VetBackend.facturacion.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturaRequestDTO {

    private Long clienteId;
    private Long citaId;
    private String estado;
    private String observaciones;

    // Lista de servicios/productos a facturar
    private List<DetalleRequestDTO> detalles;

    // Información de pago inicial (opcional)
    private PagoRequestDTO pagoInicial;

    // Constructor vacío
    public FacturaRequestDTO() {}

    // Constructor básico
    public FacturaRequestDTO(Long clienteId, Long citaId, List<DetalleRequestDTO> detalles) {
        this.clienteId = clienteId;
        this.citaId = citaId;
        this.estado = "PENDIENTE"; // Estado por defecto
        this.detalles = detalles;
    }

    // Getters y Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<DetalleRequestDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRequestDTO> detalles) {
        this.detalles = detalles;
    }

    public PagoRequestDTO getPagoInicial() {
        return pagoInicial;
    }

    public void setPagoInicial(PagoRequestDTO pagoInicial) {
        this.pagoInicial = pagoInicial;
    }

    // Métodos de validación
    public boolean isValid() {
        return clienteId != null && clienteId > 0 &&
                citaId != null && citaId > 0 &&
                detalles != null && !detalles.isEmpty() &&
                detalles.stream().allMatch(DetalleRequestDTO::isValid);
    }

    public boolean tienePagoInicial() {
        return pagoInicial != null && pagoInicial.isValid();
    }

    // Clase interna para detalles de la request
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DetalleRequestDTO {
        private Long servicioId;
        private Integer cantidad;
        private java.math.BigDecimal precioUnitario;
        private java.math.BigDecimal descuento;

        // Constructor vacío
        public DetalleRequestDTO() {}

        // Constructor básico
        public DetalleRequestDTO(Long servicioId, Integer cantidad, java.math.BigDecimal precioUnitario) {
            this.servicioId = servicioId;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.descuento = java.math.BigDecimal.ZERO;
        }

        // Getters y Setters
        public Long getServicioId() {
            return servicioId;
        }

        public void setServicioId(Long servicioId) {
            this.servicioId = servicioId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public java.math.BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(java.math.BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        public java.math.BigDecimal getDescuento() {
            return descuento;
        }

        public void setDescuento(java.math.BigDecimal descuento) {
            this.descuento = descuento;
        }

        // Validación
        public boolean isValid() {
            return servicioId != null && servicioId > 0 &&
                    cantidad != null && cantidad > 0 &&
                    precioUnitario != null && precioUnitario.compareTo(java.math.BigDecimal.ZERO) > 0;
        }
    }

    // Clase interna para pago inicial
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PagoRequestDTO {
        private String metodo;
        private java.math.BigDecimal monto;
        private String referencia;
        private String observaciones;

        // Constructor vacío
        public PagoRequestDTO() {}

        // Constructor básico
        public PagoRequestDTO(String metodo, java.math.BigDecimal monto) {
            this.metodo = metodo;
            this.monto = monto;
        }

        // Getters y Setters
        public String getMetodo() {
            return metodo;
        }

        public void setMetodo(String metodo) {
            this.metodo = metodo;
        }

        public java.math.BigDecimal getMonto() {
            return monto;
        }

        public void setMonto(java.math.BigDecimal monto) {
            this.monto = monto;
        }

        public String getReferencia() {
            return referencia;
        }

        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }

        // Validación
        public boolean isValid() {
            return metodo != null && !metodo.trim().isEmpty() &&
                    monto != null && monto.compareTo(java.math.BigDecimal.ZERO) > 0;
        }
    }
}