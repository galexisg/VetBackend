package com.Vet.VetBackend.especie.domain;

import com.Vet.VetBackend.raza.domain.Raza;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "especie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EspecieId", nullable = false)
    private Byte especieId;

    @Column(name = "Nombre", length = 60, nullable = false, unique = true)
    private String nombre;


    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Raza> razas;
}
