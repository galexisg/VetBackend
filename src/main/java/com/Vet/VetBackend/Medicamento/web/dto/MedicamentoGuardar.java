package com.Vet.VetBackend.Medicamento.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MedicamentoGuardar implements Serializable{
    private String nombre;
    private String formafarmacautica;
    private String concentracion;
    private String unidad;
    private String via;
    private String requiereReceta;
    private String activo;
    private String temperaturaalm;
    private Integer  vidautilmeses;

}


