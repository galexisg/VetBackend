package com.Vet.VetBackend.veterinario.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Especialidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EspecialidadId", nullable = false)
    private Integer especialidadId;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Activo", nullable = false)
    private Boolean activo = true; // 🔹 campo para eliminación lógica
}