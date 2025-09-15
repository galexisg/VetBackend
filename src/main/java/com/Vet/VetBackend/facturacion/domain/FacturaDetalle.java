package com.Vet.VetBackend.facturacion.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "factura_detalle")
public class FacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: factura_id (required)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    // ---- Item polymorphism: SERVICIO o TRATAMIENTO (XOR) ----
    // Both nullable at DB level; business rule enforced in @PrePersist/@PreUpdate

    @Column(name = "servicio_id")            // now NULLABLE
    private Long servicioId;

    @Column(name = "tratamiento_id")         // new column
    private Long tratamientoId;

    // Optional snapshot to "freeze" display name at the time of billing
    @Column(name = "descripcion_item", length = 200)
    private String descripcionItem;

    // ---- Pricing fields ----
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "descuento", precision = 12, scale = 2)
    private BigDecimal descuento;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    // ---- Constructors ----
    public FacturaDetalle() {}

    // Constructor for SERVICIO
    public FacturaDetalle(Factura factura, Long servicioId, Integer cantidad, BigDecimal precioUnitario) {
        this.factura = factura;
        this.servicioId = servicioId;
        this.tratamientoId = null;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = BigDecimal.ZERO;
        this.calcularSubtotal();
    }

    // Constructor for TRATAMIENTO
    public FacturaDetalle(Factura factura, Integer cantidad, BigDecimal precioUnitario, Long tratamientoId) {
        this.factura = factura;
        this.servicioId = null;
        this.tratamientoId = tratamientoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = BigDecimal.ZERO;
        this.calcularSubtotal();
    }

    // Full constructor (either servicioId or tratamientoId; never both)
    public FacturaDetalle(Factura factura, Long servicioId, Long tratamientoId,
                          Integer cantidad, BigDecimal precioUnitario, BigDecimal descuento, String descripcionItem) {
        this.factura = factura;
        this.servicioId = servicioId;
        this.tratamientoId = tratamientoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = (descuento != null) ? descuento : BigDecimal.ZERO;
        this.descripcionItem = descripcionItem;
        this.calcularSubtotal();
    }

    // ---- Getters / Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
        // Do not auto-clear tratamientoId here; XOR is enforced in callbacks
    }

    public Long getTratamientoId() { return tratamientoId; }
    public void setTratamientoId(Long tratamientoId) {
        this.tratamientoId = tratamientoId;
    }

    public String getDescripcionItem() { return descripcionItem; }
    public void setDescripcionItem(String descripcionItem) { this.descripcionItem = descripcionItem; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.calcularSubtotal();
    }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.calcularSubtotal();
    }

    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) {
        this.descuento = (descuento != null) ? descuento : BigDecimal.ZERO;
        this.calcularSubtotal();
    }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    // ---- Domain logic ----

    /** Recalculate subtotal = cantidad * precio_unitario - descuento (never below 0). */
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            BigDecimal base = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            BigDecimal desc = (descuento != null) ? descuento : BigDecimal.ZERO;
            BigDecimal result = base.subtract(desc);
            this.subtotal = (result.signum() >= 0) ? result : BigDecimal.ZERO;
        }
    }

    /** Business validation: cantidad>0, precio_unitario>0, and XOR between servicioId / tratamientoId. */
    public boolean isValid() {
        boolean qtyOk = (cantidad != null && cantidad > 0);
        boolean priceOk = (precioUnitario != null && precioUnitario.compareTo(BigDecimal.ZERO) > 0);
        boolean xor = (servicioId != null) ^ (tratamientoId != null);
        return qtyOk && priceOk && xor;
    }

    // Recalculate + enforce XOR before insert/update
    @PrePersist
    @PreUpdate
    private void prePersistUpdate() {
        this.calcularSubtotal();

        // Normalize descuento
        if (this.descuento == null) this.descuento = BigDecimal.ZERO;

        // Enforce XOR: either servicioId or tratamientoId, but not both / not none
        boolean xor = (servicioId != null) ^ (tratamientoId != null);
        if (!xor) {
            throw new IllegalStateException("Debe especificarse exactamente uno: servicio_id O tratamiento_id (XOR).");
        }

        // Defensive: subtotal must exist for DB NOT NULL
        if (this.subtotal == null) {
            throw new IllegalStateException("subtotal no puede ser null.");
        }
    }
}
