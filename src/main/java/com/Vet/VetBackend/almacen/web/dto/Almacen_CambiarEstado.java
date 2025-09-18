package com.Vet.VetBackend.almacen.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Almacen_CambiarEstado implements Serializable {
    private Boolean activo;
}

