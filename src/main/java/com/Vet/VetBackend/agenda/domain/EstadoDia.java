package com.Vet.VetBackend.agenda.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estadodia") // ⚠️ Nombre de la tabla corregido
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_dia_id")
    private Integer estadoDiaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 45, nullable = false)
    private Estado estado;


    public enum Estado {
        Activo,
        Inactivo
    }
}