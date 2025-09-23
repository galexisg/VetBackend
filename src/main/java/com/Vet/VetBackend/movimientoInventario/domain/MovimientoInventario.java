package com.Vet.VetBackend.movimientoInventario.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "movimientos_inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Status tipo;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "almacen_id")
    private Integer almacenId;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "observacion")
    private String observacion;

    public enum Status {
        ENTRADA,
        SALIDA
    }
}
