package com.Vet.VetBackend.dispensa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
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

    // private Integer usuarioId;
}
