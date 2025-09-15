package com.Vet.VetBackend.tratamientos.web.dto;

import lombok.Data;

@Data
public class TratamientoReq {
    private String nombre;
    private String descripcion;
    private Integer duracionDias;
    private String frecuencia;
    private String via;
}



