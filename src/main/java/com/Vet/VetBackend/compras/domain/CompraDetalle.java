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
    private Long id;

    @Column(name = "compra_id")
    private Long compraId;

    @Column(name = "producto_id")
    private Long productoId;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio", nullable = false)
    private double precio;
}
