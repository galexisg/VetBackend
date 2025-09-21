package com.Vet.VetBackend.facturacion.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @Column(name = "metodo", length = 20, nullable = false)
    private String metodo; // EFECTIVO | TARJETA | TRANSFERENCIA | CHEQUE

    @Column(name = "monto", precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // --- ctors ---
    public Pago() {}

    public Pago(Factura factura, String metodo, BigDecimal monto) {
        this(factura, metodo, monto, LocalDateTime.now());
    }

    public Pago(Factura factura, String metodo, BigDecimal monto, LocalDateTime fechaPago) {
        this.factura = factura;
        // yo normalizo a UPPER para consistencia
        this.metodo = metodo != null ? metodo.trim().toUpperCase() : null;
        this.monto = monto;
        this.fechaPago = (fechaPago != null) ? fechaPago : LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    // --- getters/setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo != null ? metodo.trim().toUpperCase() : null; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // --- callbacks ---
    @PrePersist
    public void prePersist() {
        if (this.metodo != null) this.metodo = this.metodo.trim().toUpperCase();
        if (this.fechaPago == null) this.fechaPago = LocalDateTime.now();
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.monto == null) this.monto = BigDecimal.ZERO; // defensivo; tu servicio valida > 0
    }

    // --- validaciones utilitarias ---
    public boolean isValid() {
        return factura != null &&
                metodo != null && !metodo.isBlank() &&
                monto != null && monto.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isMetodoEfectivo()       { return "EFECTIVO".equalsIgnoreCase(this.metodo); }
    public boolean isMetodoTarjeta()        { return "TARJETA".equalsIgnoreCase(this.metodo); }
    public boolean isMetodoTransferencia()  { return "TRANSFERENCIA".equalsIgnoreCase(this.metodo); }
    public boolean isMetodoCheque()         { return "CHEQUE".equalsIgnoreCase(this.metodo); }

    // Opcional: catálogo de métodos
    public enum MetodoPago {
        EFECTIVO, TARJETA, TRANSFERENCIA, CHEQUE
    }
}
