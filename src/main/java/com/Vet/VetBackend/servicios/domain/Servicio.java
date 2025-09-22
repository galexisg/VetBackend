package com.Vet.VetBackend.servicios.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "servicio",
        uniqueConstraints = @UniqueConstraint(name = "uk_servicio_nombre", columnNames = "nombre")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 250)
    private String descripcion;

    @Column(name = "precio_base", precision = 12, scale = 2)
    private BigDecimal precioBase;

    @Enumerated(EnumType.STRING) // Se guarda como texto (ACTIVO, INACTIVO)
    @Column(nullable = false, length = 50)
    @Builder.Default
    private EstadoServicio estado = EstadoServicio.ACTIVO;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public enum EstadoServicio {
        ACTIVO,
        INACTIVO
    }

    // Métodos auxiliares para compatibilidad con el viejo "activo"
    public boolean isActivo() {
        return this.estado == EstadoServicio.ACTIVO;
    }

    public void activar() {
        this.estado = EstadoServicio.ACTIVO;
    }

    public void desactivar() {
        this.estado = EstadoServicio.INACTIVO;
    }
}
