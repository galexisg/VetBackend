package com.Vet.VetBackend.movimientoInventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class MovimientoInventario_Guardar implements Serializable {
    private Date fecha;

    private String observacion;

    private Integer almacenId;

    private Integer usuarioId;
}