package com.Vet.VetBackend.agenda.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DetalleHorarioVeterinario")
public class DetalleHorarioVeterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DetalleHorarioVeterinarioId")
    private Integer detalleHorarioVeterinarioId;


//    @ManyToOne
//    @JoinColumn(name = "VeterinarioId", referencedColumnName = "VeterinarioId")
//    private Veterinario veterinario;
//
//
//    @ManyToOne
//    @JoinColumn(name = "DiaId", referencedColumnName = "DiaId")
//    private Dia dia;

    // Relación con BloqueHorario
    @ManyToOne
    @JoinColumn(name = "BloqueHorarioId", referencedColumnName = "BloqueHorarioId")
    private BloqueHorario bloqueHorario;
}