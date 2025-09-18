package com.Vet.VetBackend.proveedores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String nit;
    private String telefono;
    private String email;
    private String direccion;
    private Integer estadoid;  // 👈 Campo agregado, puede ser NULL
    private String notas;
}
