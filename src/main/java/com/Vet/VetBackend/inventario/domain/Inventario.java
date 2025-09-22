package com.Vet.VetBackend.inventario.domain;

import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.almacen.domain.Almacen;
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

    @Column(name = "stock_actual", nullable = false)
    private double stockActual;

    @Column(name = "stock_minimo", nullable = false)
    private double stockMinimo;

    @Column(name = "stock_maximo", nullable = false)
    private double stockMaximo;

//    @ManyToOne
//    @JoinColumn(name = "almacen_id")
//    private Almacen almacen;
//
//    @ManyToOne
//    @JoinColumn(name = "medicamento_id")
//    private Medicamento medicamento;
}
