package com.Vet.VetBackend.diagnostico.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class DiagnosticoDto {
    private Long id;
    private Long citaId;
    private String nombre;
    private String descripcion;
    @JsonProperty("estadoDiagnostico")   // 👈 así tu API expone 'estadoDiagnostico'
    private boolean estadoDiagnostico;
    private LocalDateTime creadoAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
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

    public boolean isEstadoDiagnostico() {
        return estadoDiagnostico;
    }

    public void setEstadoDiagnostico(boolean estadoDiagnostico) {
        this.estadoDiagnostico = estadoDiagnostico;
    }

    public LocalDateTime getCreadoAt() {
        return creadoAt;
    }

    public void setCreadoAt(LocalDateTime creadoAt) {
        this.creadoAt = creadoAt;
    }
}
