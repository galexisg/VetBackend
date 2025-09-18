package com.Vet.VetBackend.dispensa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Dispensa")
public class Dispensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer prescripcionDetalleId;

    private Integer almacenId;

    private Integer loteId;

    @Column(precision = 12, scale = 2)
    private BigDecimal cantidad;

    private LocalDateTime fecha;

    private Integer usuarioId;
}
