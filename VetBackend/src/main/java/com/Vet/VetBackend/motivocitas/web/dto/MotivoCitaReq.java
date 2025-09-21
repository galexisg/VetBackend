package com.Vet.VetBackend.motivocitas.web.dto;

import com.Vet.VetBackend.motivocitas.domain.MotivoCita;

public class MotivoCitaReq {
    private String nombre;
    private String descripcion;

    public MotivoCitaReq() {}

    public MotivoCitaReq(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Conversión DTO → Entidad
    public MotivoCita toEntity() {
        MotivoCita motivo = new MotivoCita();
        motivo.setNombre(this.nombre);
        motivo.setDescripcion(this.descripcion);
        // El campo "activo" se puede setear en true por defecto al crear
        motivo.setActivo(true);
        return motivo;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}