package com.Vet.VetBackend.dispensa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dispensa")
public class Dispensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "almacen_id")
    private Integer almacenId;

    @Column(precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime fecha;

    @Column(name = "lote_id")
    private Integer loteId;

    @Column(name = "prescripcion_detalle_id")
    private Integer prescripcionDetalleId;

    @Column(name = "usuario_id")
    private Integer usuarioId;  //  Nuevo campo para la relación con Usuario
}
