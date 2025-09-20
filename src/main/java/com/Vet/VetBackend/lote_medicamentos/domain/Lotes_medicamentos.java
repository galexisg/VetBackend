package com.Vet.VetBackend.lote_medicamentos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "lote_medicamento")
public class Lotes_medicamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "medicamento_id")
    private Integer medicamentoId;

    @Column(name = "proveedor_id")
    private Integer proveedorId;

    @Column(name = "codigo_lote", length = 60)
    private String codigoLote;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "observaciones", length = 200)
    private String observaciones;
}
