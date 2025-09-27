package com.Vet.VetBackend.proveedores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "proveedor") // 👈 nombre en minúsculas para coincidir con la tabla
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "nit", length = 255)
    private String nit;

    @Column(name = "telefono", length = 255)
    private String telefono;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "estadoid")
    private Integer estadoid;

    @Column(name = "notas", length = 255)
    private String notas;
}
