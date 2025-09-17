package com.Vet.VetBackend.usuarios.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRol")
    private Byte id; // TINYINT -> Byte en Java

    @Column(name = "Nombre", length = 45, nullable = false)
    private String nombre;

    // --- ctors ---
    public Rol() {}

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    // --- getters/setters ---
    public Byte getId() { return id; }
    public void setId(Byte id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
