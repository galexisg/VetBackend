package com.Vet.VetBackend.usuarios.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEstado")
    private Byte id; // TINYINT -> Byte en Java

    @Column(name = "Nombre", length = 45, nullable = false)
    private String nombre;

    // --- ctors ---
    public Estado() {}

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    // --- getters/setters ---
    public Byte getId() { return id; }
    public void setId(Byte id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
