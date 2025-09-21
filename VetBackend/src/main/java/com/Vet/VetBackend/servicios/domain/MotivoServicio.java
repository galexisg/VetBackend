package com.Vet.VetBackend.servicios.domain;

import jakarta.persistence.*; import lombok.*;

@Entity @Table(name="motivo_servicio",
        uniqueConstraints=@UniqueConstraint(name="uq_motivo_servicio",columnNames={"motivo_id","servicio_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MotivoServicio {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="motivo_id",
            foreignKey=@ForeignKey(name="fk_ms_motivo")) private Motivo motivo;

    @ManyToOne(optional=false) @JoinColumn(name="servicio_id",
            foreignKey=@ForeignKey(name="fk_ms_servicio")) private Servicio servicio;
}
