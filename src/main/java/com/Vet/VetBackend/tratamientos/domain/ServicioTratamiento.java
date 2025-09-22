package com.Vet.VetBackend.tratamientos.domain;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "servicio_tratamiento")
public class ServicioTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")                 // ← doc: id
    private Long id;

    @Column(name = "servicio_id", nullable = false)     // ← doc
    private Long servicioId;

    @Column(name = "tratamiento_id", nullable = false)  // ← doc
    private Long tratamientoId;

    @Column(nullable = false)
    private boolean activo = true;  // por defecto activo
}


