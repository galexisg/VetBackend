package com.Vet.VetBackend.lote_medicamentos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LoteMedicamento")
public class Lotes_medicamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer medicamentoId;

    private Integer proveedorId;

    @Column(length = 60)
    private String codigoLote;

    private java.sql.Date fechaVencimiento;

    @Column(length = 200)
    private String observaciones;
}
