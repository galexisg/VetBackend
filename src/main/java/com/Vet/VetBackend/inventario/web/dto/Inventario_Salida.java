package com.Vet.VetBackend.inventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Inventario_Salida implements Serializable {
    private Integer id;

    private double stockActual;

    private double stockMinimo;

    private double stockMaximo;

    //private Almacen_Salida almacen;

    //private MedicamentoSalida medicamento;
}
