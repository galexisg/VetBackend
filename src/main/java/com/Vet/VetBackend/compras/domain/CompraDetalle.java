package com.Vet.VetBackend.compras.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "compra_detalle")
@Getter
@Setter
public class CompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "compra_id")
    private Integer compraId;

    @Column(name = "producto_id")
    private Integer productoId;

    private int cantidad;

    private double precio;
}

