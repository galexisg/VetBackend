package com.Vet.VetBackend.facturacion.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // BD: INT NULL -> en Java usamos Integer
    @Column(name = "cliente_id", nullable = true)
    private Integer clienteId;

    // Si la PK de cita es BIGINT, aquí va Long
    @Column(name = "cita_id", nullable = true)
    private Long citaId;

    // Campo presente en la tabla y opcional (NULL)
    @Column(name = "usuario_id", nullable = true)
    private Integer usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "estado",
            nullable = false,
            columnDefinition = "ENUM('PENDIENTE','PARCIAL','PAGADA','ANULADA')"
    )
    private FacturaEstado estado;

    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "saldo", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "motivo_anulacion", length = 250)
    private String motivoAnulacion;

    // BD: INT NULL
    @Column(name = "usuario_anula_id", nullable = true)
    private Integer usuarioAnulaId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relaciones
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacturaDetalle> detalles;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;

    public Factura() {}

    public Factura(Integer clienteId, Long citaId, FacturaEstado estado, BigDecimal total) {
        this.clienteId = clienteId;
        this.citaId = citaId;
        this.estado = estado;
        this.total = total;
        this.saldo = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public FacturaEstado getEstado() { return estado; }
    public void setEstado(FacturaEstado estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public String getMotivoAnulacion() { return motivoAnulacion; }
    public void setMotivoAnulacion(String motivoAnulacion) { this.motivoAnulacion = motivoAnulacion; }

    public Integer getUsuarioAnulaId() { return usuarioAnulaId; }
    public void setUsuarioAnulaId(Integer usuarioAnulaId) { this.usuarioAnulaId = usuarioAnulaId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<FacturaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<FacturaDetalle> detalles) { this.detalles = detalles; }

    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = FacturaEstado.PENDIENTE;
        if (this.total == null) this.total = BigDecimal.ZERO;
        if (this.saldo == null) this.saldo = BigDecimal.ZERO;
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void calcularSaldo() {
        if (pagos != null && !pagos.isEmpty()) {
            BigDecimal totalPagos = pagos.stream()
                    .map(Pago::getMonto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            this.saldo = this.total.subtract(totalPagos);
        } else {
            this.saldo = this.total;
        }
    }
}
