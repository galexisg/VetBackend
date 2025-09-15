package com.Vet.VetBackend.tratamientos.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tratamiento_aplicado")
public class TratamientoAplicado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // id
    private Long id;

    @Column(name = "cita_id", nullable = false)
    private Long citaId;

    @Column(name = "tratamiento_id", nullable = false)
    private Long tratamientoId;

    @Column(name = "historial_id", nullable = false)
    private Long historialId;

    @Column(name = "veterinario_id", nullable = false)
    private Long veterinarioId;

    @Column(name = "estado", length = 20)
    private String estado; // Planificado, EnCurso, Completado, Cancelado

    @Column(name = "observaciones", length = 500)
    private String observaciones;
}
