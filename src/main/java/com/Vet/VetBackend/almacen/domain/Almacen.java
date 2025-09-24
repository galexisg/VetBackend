package com.Vet.VetBackend.almacen.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "almacen")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 80)
    private String nombre;

    @Column(length = 200)
    private String ubicacion;

    @Column(name = "activo")
    private Boolean activo;
}
