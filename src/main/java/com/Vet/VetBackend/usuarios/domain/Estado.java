package com.Vet.VetBackend.usuarios.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEstado")
    private Byte id;

    @Column(name = "Nombre", nullable = false, length = 45)
    private String nombre;

    // Getters y Setters
    public Byte getId() { return id; }
    public void setId(Byte id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}

