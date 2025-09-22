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
    @Column(name = "raza_id") // <-- igual que en la BD
    private  Byte id;

    // por ahora lo dejamos como columna simple
    @Column(name = "especie_id", nullable = false) // <-- igual que en la BD
    private  Byte especieId;

    @Column(name = "nombre", length = 60, nullable = false) // <-- igual que en la BD
    private String nombre;
}
