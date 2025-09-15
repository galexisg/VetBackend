package com.Vet.VetBackend.facturacion.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagoDTO {

    private Long id;
    private Long facturaId;
    private String metodo;
    private BigDecimal monto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPago;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // Información adicional (no en BD pero útil para APIs)
    private String numeroFactura;
    private String nombreCliente;
    private String metodoDescripcion;
    private String referencia; // Para transferencias, cheques, etc.
    private String observaciones;

    // Constructor vacío
    public PagoDTO() {}

    // Constructor básico
    public PagoDTO(String metodo, BigDecimal monto) {
        this.metodo = metodo;
        this.monto = monto;
        this.fechaPago = LocalDateTime.now();
    }

    // Constructor completo
    public PagoDTO(Long facturaId, String metodo, BigDecimal monto, LocalDateTime fechaPago) {
        this.facturaId = facturaId;
        this.metodo = metodo;
        this.monto = monto;
        this.fechaPago = fechaPago != null ? fechaPago : LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getMetodoDescripcion() {
        return metodoDescripcion;
    }

    public void setMetodoDescripcion(String metodoDescripcion) {
        this.metodoDescripcion = metodoDescripcion;
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

    // Métodos de utilidad
    public boolean isEfectivo() {
        return "EFECTIVO".equalsIgnoreCase(metodo);
    }

    public boolean isTarjeta() {
        return "TARJETA".equalsIgnoreCase(metodo);
    }

    public boolean isTransferencia() {
        return "TRANSFERENCIA".equalsIgnoreCase(metodo);
    }

    public boolean isCheque() {
        return "CHEQUE".equalsIgnoreCase(metodo);
    }

    public boolean isValid() {
        return monto != null && monto.compareTo(BigDecimal.ZERO) > 0 &&
                metodo != null && !metodo.trim().isEmpty() &&
                facturaId != null && facturaId > 0;
    }

    public String getMetodoFormateado() {
        if (metodo == null) return "";

        switch (metodo.toLowerCase()) {
            case "efectivo":
                return "Efectivo";
            case "tarjeta":
                return "Tarjeta";
            case "transferencia":
                return "Transferencia";
            case "cheque":
                return "Cheque";
            default:
                return metodo;
        }
    }
}