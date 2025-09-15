package com.Vet.VetBackend.facturacion.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturaDetalleDTO {

    private Long id;
    private Long facturaId;
    private Long servicioId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    // Información adicional (no en BD pero útil para APIs)
    private String nombreServicio;
    private String descripcionServicio;
    private BigDecimal precioBaseServicio;
    private BigDecimal totalLinea; // subtotal calculado automáticamente
    private BigDecimal porcentajeDescuento;

    // Constructor vacío
    public FacturaDetalleDTO() {}

    // Constructor básico
    public FacturaDetalleDTO(Long servicioId, Integer cantidad, BigDecimal precioUnitario) {
        this.servicioId = servicioId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = BigDecimal.ZERO;
        this.calcularSubtotal();
    }

    // Constructor completo
    public FacturaDetalleDTO(Long servicioId, Integer cantidad, BigDecimal precioUnitario,
                             BigDecimal descuento, String nombreServicio) {
        this.servicioId = servicioId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento != null ? descuento : BigDecimal.ZERO;
        this.nombreServicio = nombreServicio;
        this.calcularSubtotal();
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
        this.calcularSubtotal();
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.calcularSubtotal();
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento != null ? descuento : BigDecimal.ZERO;
        this.calcularSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public BigDecimal getPrecioBaseServicio() {
        return precioBaseServicio;
    }

    public void setPrecioBaseServicio(BigDecimal precioBaseServicio) {
        this.precioBaseServicio = precioBaseServicio;
    }

    public BigDecimal getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(BigDecimal totalLinea) {
        this.totalLinea = totalLinea;
    }

    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // Métodos de utilidad
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            BigDecimal montoBase = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            BigDecimal descuentoAplicar = descuento != null ? descuento : BigDecimal.ZERO;
            this.subtotal = montoBase.subtract(descuentoAplicar);
            this.totalLinea = this.subtotal;

            // Calcular porcentaje de descuento
            if (montoBase.compareTo(BigDecimal.ZERO) > 0 && descuentoAplicar.compareTo(BigDecimal.ZERO) > 0) {
                this.porcentajeDescuento = descuentoAplicar.divide(montoBase, 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            }
        }
    }

    public boolean tieneDescuento() {
        return descuento != null && descuento.compareTo(BigDecimal.ZERO) > 0;
    }

    public BigDecimal getMontoSinDescuento() {
        if (cantidad != null && precioUnitario != null) {
            return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        return BigDecimal.ZERO;
    }

    public boolean isValid() {
        return servicioId != null && servicioId > 0 &&
                cantidad != null && cantidad > 0 &&
                precioUnitario != null && precioUnitario.compareTo(BigDecimal.ZERO) > 0;
    }
}