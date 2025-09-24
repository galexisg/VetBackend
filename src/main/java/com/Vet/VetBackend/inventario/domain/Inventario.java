package com.Vet.VetBackend.inventario.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stock_actual", nullable = false)
    private double stockActual;

    @Column(name = "stock_minimo", nullable = false)
    private double stockMinimo;

    @Column(name = "stock_maximo", nullable = false)
    private double stockMaximo;

    @Column(name = "medicamento_id")
    private Integer medicamentoId;

    @Column(name = "almacen_id")
    private Integer almacenId;
}
