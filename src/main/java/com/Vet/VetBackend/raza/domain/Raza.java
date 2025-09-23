package com.Vet.VetBackend.raza.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "raza")
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raza_id")
    private Byte id; // 👈 usar Integer para ser consistente con el repo y DTOs

    @Column(name = "especie_id", nullable = false)
    private Byte especieId;

    @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;
}
