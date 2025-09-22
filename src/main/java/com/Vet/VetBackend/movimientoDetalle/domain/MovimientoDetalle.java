package com.Vet.VetBackend.movimientoDetalle.domain;

import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.lote_medicamentos.domain.Lotes_medicamentos;
import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
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

    @Column(name = "costo_unitario")
    private double costoUnitario;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "movimiento_id")
    private MovimientoInventario movimientoInventario;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lotes_medicamentos loteMedicamento;

    @ManyToOne
    @JoinColumn(name = "almacen_id")
    private Almacen almacen;
}

