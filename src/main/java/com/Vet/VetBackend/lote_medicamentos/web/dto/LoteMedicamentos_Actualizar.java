package com.Vet.VetBackend.lote_medicamentos.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class LoteMedicamentos_Actualizar implements Serializable {

        private Integer id;
        private String codigoLote;
        private Date fechaVencimiento;
        private Integer medicamentoId;
        private Integer proveedorId;
        private String observaciones;
    }


