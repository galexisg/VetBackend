package com.Vet.VetBackend.agenda.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "BloqueHorario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloqueHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BloqueHorarioId", nullable = false)
    private Integer bloqueHorarioId;

    @Column(name = "Inicio", nullable = false)
    private LocalTime inicio;

    @Column(name = "Fin", nullable = false)
    private LocalTime fin;

    @Column(name = "Activo", nullable = false)
    private Boolean activo = true; // 🔹 eliminación lógica
}
