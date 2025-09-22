package com.Vet.VetBackend.movimientoInventario.domain;

import com.Vet.VetBackend.almacen.domain.Almacen;
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
    private Status tipo;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "almacen_id")
    private Almacen almacen;

    //@ManyToOne
    //@JoinColumn(name = "usuario_id")
    //private Usuario usuario;

    private String observacion;

    public static enum Status {
        ENTRADA,
        SALIDA
    }
}
