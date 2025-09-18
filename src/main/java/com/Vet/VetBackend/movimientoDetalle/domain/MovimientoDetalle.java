package com.Vet.VetBackend.movimientoDetalle.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "MovimientoDetalle")
public class MovimientoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double cantidad;

    private double costoUnitario;

    private LocalDateTime fecha;

    private Integer usuarioId;


    //@ManyToOne
    //@JoinColumn(name = "movimiento_id")
    //private MovimientoInventario movimientoInventario;

    //@ManyToOne
    //@JoinColumn(name = "medicamento_id")
    //private Medicamento medicamento;

    //@ManyToOne
    //@JoinColumn(name = "lote_id")
    //private Lotes_medicamentos lotesMedicamentos;

    //@ManyToOne
    //@JoinColumn(name = "almacen_id")
    //private Almacen almacen;

}

