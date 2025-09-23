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

    @ManyToOne
    @JoinColumn(name = "compra_id", foreignKey = @ForeignKey(name = "fk_compra_det__compra"))
    private Compra compra;

    @Column(name = "compra_id")
    private Long compraId;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio", nullable = false)
    private double precio;

    // 🔴 Campo eliminado: productoId
    // Si decides agregarlo más adelante, puedes descomentar:
    // @Column(name = "producto_id")
    // private Long productoId;
}
