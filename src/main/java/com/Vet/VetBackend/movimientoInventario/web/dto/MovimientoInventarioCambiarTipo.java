package com.Vet.VetBackend.movimientoInventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MovimientoInventarioCambiarTipo implements Serializable {
    private Integer id;

    private String tipo;
}
