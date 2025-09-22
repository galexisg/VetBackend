package com.Vet.VetBackend.agenda.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dia") // ⚠️ El nombre de la tabla ahora es "dia"
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dia_id")
    private int diaId;

    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_dia_id", nullable = false)
    private EstadoDia estadoDia;
}