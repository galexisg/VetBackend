package com.Vet.VetBackend.inventario.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double stockActual;

    private double stockMinimo;

    private double stockMaximo;

    //@ManyToOne
    //@JoinTable(name = "almacen_id",)
    //private Almacen almacen;

    //@ManyToOne
    //@JoinTable(name = "medicamento_id")
    //private Medicamento medicamento;
}
