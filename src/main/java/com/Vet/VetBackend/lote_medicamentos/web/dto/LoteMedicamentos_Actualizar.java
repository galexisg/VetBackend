package com.Vet.VetBackend.lote_medicamentos.web.dto;

import lombok.Getter;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoteMedicamentos_Actualizar {
        private Integer id;
        private String codigoLote;
        private Date fechaVencimiento;
        private String observaciones;
        private Integer medicamentoId;
        private Integer proveedorId;
}


