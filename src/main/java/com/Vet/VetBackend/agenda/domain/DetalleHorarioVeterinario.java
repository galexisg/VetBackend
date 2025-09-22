package com.Vet.VetBackend.agenda.domain;

import com.Vet.VetBackend.veterinario.domain.Veterinario;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detalle_horario_veterinario")
public class DetalleHorarioVeterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_horario_veterinario_id")
    private Integer detalleHorarioVeterinarioId;

    @ManyToOne
    @JoinColumn(name = "veterinario_id", referencedColumnName = "veterinario_id")
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "dia_id", referencedColumnName = "dia_id")
    private Dia dia;

    @ManyToOne
    @JoinColumn(name = "bloque_horario_id", referencedColumnName = "bloque_horario_id")
    private BloqueHorario bloqueHorario;
}
