package com.Vet.VetBackend.almacen.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Almacen_Salida implements Serializable {
    private Integer id;
    private String nombre;
    private String ubicacion;
    private Boolean activo;
}

