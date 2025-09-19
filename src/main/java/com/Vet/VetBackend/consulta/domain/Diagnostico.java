package com.Vet.VetBackend.consulta.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="diagnostico")

public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnostico_id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 225, unique = true)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @OneToMany(mappedBy = "diagnostico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultaDiagnostico> consultaDiagnosticos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ConsultaDiagnostico> getConsultaDiagnosticos() {
        return consultaDiagnosticos;
    }

    public void setConsultaDiagnosticos(List<ConsultaDiagnostico> consultaDiagnosticos) {
        this.consultaDiagnosticos = consultaDiagnosticos;
    }
}
