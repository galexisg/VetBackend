package com.Vet.VetBackend.inventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Inventario_Guardar implements Serializable {
    private double stockActual;

    private double stockMinimo;

    private double stockMaximo;

    private Integer almacenId;

    private Integer medicamentoId;
}