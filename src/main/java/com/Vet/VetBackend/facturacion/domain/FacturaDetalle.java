package com.Vet.VetBackend.facturacion.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "factura_detalle")
public class FacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "descuento", precision = 12, scale = 2)
    private BigDecimal descuento;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    // Constructor vacío (requerido por JPA)
    public FacturaDetalle() {}

    // Constructor con campos obligatorios
    public FacturaDetalle(Factura factura, Long servicioId, Integer cantidad, BigDecimal precioUnitario) {
        this.factura = factura;
        this.servicioId = servicioId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = BigDecimal.ZERO;
        this.calcularSubtotal();
    }

    // Constructor completo
    public FacturaDetalle(Factura factura, Long servicioId, Integer cantidad,
                          BigDecimal precioUnitario, BigDecimal descuento) {
        this.factura = factura;
        this.servicioId = servicioId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento != null ? descuento : BigDecimal.ZERO;
        this.calcularSubtotal();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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
        this.calcularSubtotal(); // Recalcula automáticamente
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.calcularSubtotal(); // Recalcula automáticamente
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento != null ? descuento : BigDecimal.ZERO;
        this.calcularSubtotal(); // Recalcula automáticamente
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    // Método para calcular subtotal automáticamente
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            BigDecimal montoBase = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            BigDecimal descuentoAplicar = descuento != null ? descuento : BigDecimal.ZERO;
            this.subtotal = montoBase.subtract(descuentoAplicar);
        }
    }

    // Método de callback para recalcular antes de persistir
    @PrePersist
    @PreUpdate
    public void calcularSubtotalCallback() {
        this.calcularSubtotal();
    }

    // Validaciones
    public boolean isValid() {
        return cantidad != null && cantidad > 0 &&
                precioUnitario != null && precioUnitario.compareTo(BigDecimal.ZERO) > 0 &&
                servicioId != null && servicioId > 0;
    }
}