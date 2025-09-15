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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @Column(name = "metodo", length = 20, nullable = false)
    private String metodo;

    @Column(name = "monto", precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Constructor vacío (requerido por JPA)
    public Pago() {}

    // Constructor con campos obligatorios
    public Pago(Factura factura, String metodo, BigDecimal monto) {
        this.factura = factura;
        this.metodo = metodo;
        this.monto = monto;
        this.fechaPago = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    // Constructor completo
    public Pago(Factura factura, String metodo, BigDecimal monto, LocalDateTime fechaPago) {
        this.factura = factura;
        this.metodo = metodo;
        this.monto = monto;
        this.fechaPago = fechaPago != null ? fechaPago : LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
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

    // Métodos de callback JPA
    @PrePersist
    public void prePersist() {
        if (this.fechaPago == null) {
            this.fechaPago = LocalDateTime.now();
        }
        this.createdAt = LocalDateTime.now();
    }

    // Validaciones
    public boolean isValid() {
        return monto != null && monto.compareTo(BigDecimal.ZERO) > 0 &&
                metodo != null && !metodo.trim().isEmpty() &&
                factura != null;
    }

    // Métodos de utilidad
    public boolean isMetodoEfectivo() {
        return "EFECTIVO".equalsIgnoreCase(this.metodo);
    }

    public boolean isMetodoTarjeta() {
        return "TARJETA".equalsIgnoreCase(this.metodo);
    }

    public boolean isMetodoTransferencia() {
        return "TRANSFERENCIA".equalsIgnoreCase(this.metodo);
    }

    // Enum para métodos de pago (opcional - puedes usarlo como constantes)
    public enum MetodoPago {
        EFECTIVO("EFECTIVO"),
        TARJETA("TARJETA"),
        TRANSFERENCIA("TRANSFERENCIA"),
        CHEQUE("CHEQUE");

        private final String valor;

        MetodoPago(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }
    }
}