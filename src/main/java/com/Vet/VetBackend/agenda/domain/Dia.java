package com.Vet.VetBackend.agenda.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiaId")
    private int diaId;

    @Column(name = "Nombre", length = 20, nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EstadoDiaId", nullable = false)
    private EstadoDia estadoDia;
}