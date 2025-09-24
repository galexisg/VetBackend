package com.Vet.VetBackend.compras.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "compra_detalle")
public class CompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "compra_id")
    private Long compraId;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", nullable = false)
    private Double precio;

    // 🔴 Campo eliminado: productoId
    // Si decides agregarlo más adelante, puedes descomentar:
    // @Column(name = "producto_id")
    // private Long productoId;
}
