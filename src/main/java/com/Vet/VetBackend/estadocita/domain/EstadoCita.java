package com.Vet.VetBackend.estadocita.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cita_estado")
public class EstadoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cita_estado_id")
    private Integer id;

    @Column(name = "nombre", length = 40, nullable = false, unique = true)
    private String nombre;
}

