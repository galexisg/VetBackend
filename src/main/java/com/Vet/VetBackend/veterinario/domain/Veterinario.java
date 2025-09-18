package com.Vet.VetBackend.veterinario.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veterinario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class   Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "veterinario_id")
    private int id;

    @Column(name = "numero_licencia", length = 60, nullable = false)
    private String numeroLicencia;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;


    public enum Estado {
        Activo,
        Inactivo
    }
}
