package com.Vet.VetBackend.veterinario.domain;

import com.Vet.VetBackend.servicios.domain.Servicio;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.vacuna.domain.Vacuna;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "veterinario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {

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

    // 🔹 Relación uno a uno con usuario
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuarioId", nullable = false)
    private Usuario usuario;

    // 🔹 Relación muchos a uno con especialidad (una sola especialidad por veterinario)
    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    // 🔹 Relación muchos a uno con servicio (un solo servicio por veterinario)
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicios;


}


