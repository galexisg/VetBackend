package com.Vet.VetBackend.movimientoDetalle.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MovimientoDetalle_Salida implements Serializable {
    private Integer id;

    private double cantidad;

    private double costoUnitario;

    //private MovimientoInventario_Salida movimientoInventario;

    //private MedicamentoSalida medicamento;

    //private LoteMedicamento_Salida loteMedicamento;
}
