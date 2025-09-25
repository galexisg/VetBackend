package com.Vet.VetBackend.raza.domain;

import com.Vet.VetBackend.especie.domain.Especie;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "raza")
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RazaId")
    private Byte id;

    @Column(name = "Nombre", length = 60, nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especie_id", nullable = false) // FK
    private Especie especie;
}
