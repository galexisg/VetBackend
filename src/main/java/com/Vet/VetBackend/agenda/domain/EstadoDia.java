package com.Vet.VetBackend.agenda.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EstadoDiaId")
    private Integer estadoDiaId;

    @Column(name = "Nombre", length = 45, nullable = false)
    private String nombre;
}