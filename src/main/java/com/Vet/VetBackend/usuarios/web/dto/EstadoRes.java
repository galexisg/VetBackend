// src/main/java/com/Vet/VetBackend/usuarios/web/dto/EstadoRes.java
package com.Vet.VetBackend.usuarios.web.dto;

public class EstadoRes {
    private Byte id;
    private String nombre;

    public EstadoRes(Byte id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Byte getId() { return id; }
    public String getNombre() { return nombre; }
}
