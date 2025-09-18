// src/main/java/com/Vet/VetBackend/usuarios/web/dto/RolRes.java
package com.Vet.VetBackend.usuarios.web.dto;

public class RolRes {
    private Byte id;
    private String nombre;

    public RolRes(Byte id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Byte getId() { return id; }
    public String getNombre() { return nombre; }
}
