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

    // 🔹 Relación muchos a muchos con especialidad
    @ManyToMany
    @JoinTable(
            name = "veterinario_especialidad",
            joinColumns = @JoinColumn(name = "veterinario_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private Set<Especialidad> especialidades = new HashSet<>();

    public enum Estado {
        Activo,
        Inactivo
    }

    // 🔹 Relación uno a uno con usuario
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuarioId", nullable = false)
    private Usuario usuario;

    // 🔹 Relación muchos a muchos con servicio
    @ManyToMany
    @JoinTable(
            name = "veterinario_servicio",
            joinColumns = @JoinColumn(name = "veterinario_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private Set<Servicio> servicios = new HashSet<>();
//
//    // 🔹 Relación uno a muchos con emergencias
//    @OneToMany(mappedBy = "veterinario")
//    private Set<Emergencia> emergencias = new HashSet<>();

//    // 🔹 Relación uno a muchos con prescripciones
//    @OneToMany(mappedBy = "veterinario")
//    private Set<Prescripcion> prescripciones = new HashSet<>();

    // 🔹 Relación uno a muchos con historial de vacunas
    @OneToMany(mappedBy = "veterinario")
    private Set<Vacuna> vacunasAplicadas = new HashSet<>();
}
