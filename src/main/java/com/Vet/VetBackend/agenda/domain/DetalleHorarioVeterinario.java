package com.Vet.VetBackend.agenda.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detalle_horario_veterinario") // Nombre de la tabla
public class DetalleHorarioVeterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_horario_veterinario_id") // Nombre de la columna
    private Integer detalleHorarioVeterinarioId;

//    @ManyToOne
//    @JoinColumn(name = "veterinario_id", referencedColumnName = "veterinario_id") // Clave foránea
//    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "dia_id", referencedColumnName = "dia_id") // Clave foránea
    private Dia dia;

    @ManyToOne
    @JoinColumn(name = "bloque_horario_id", referencedColumnName = "bloque_horario_id") // Clave foránea
    private BloqueHorario bloqueHorario;
}