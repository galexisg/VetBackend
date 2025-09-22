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
    @Column(name = "dia_id") // ⚠️ El nombre de la columna es "dia_id"
    private int diaId;

    @Column(name = "nombre", length = 20, nullable = false) // ⚠️ El nombre de la columna es "nombre"
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_dia_id", nullable = false) // ⚠️ El nombre de la columna es "estado_dia_id"
    private EstadoDia estadoDia;
}