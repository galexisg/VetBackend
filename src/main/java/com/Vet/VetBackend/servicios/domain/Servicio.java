package com.Vet.VetBackend.servicios.domain;
import jakarta.persistence.*; import lombok.*;
import java.math.BigDecimal; import java.time.LocalDateTime;

@Entity @Table(name="servicio",
        uniqueConstraints=@UniqueConstraint(name="uk_servicio_nombre",columnNames="nombre"))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Servicio {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=120) private String nombre;
    @Column(length=250) private String descripcion;
    @Column(name="precio_base",precision=12,scale=2) private BigDecimal precioBase;
    @Column(nullable=false) private Boolean activo=true;
    @Column(name="created_at",updatable=false, insertable=false) private LocalDateTime createdAt;
    @Column(name="updated_at",insertable=false) private LocalDateTime updatedAt;
}
