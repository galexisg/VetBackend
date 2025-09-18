package com.Vet.VetBackend.movimientoInventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class MovimientoInventario_Modificar implements Serializable {
    private Integer Id;

    private String tipo;

    private Date fecha;

    private String observacion;

    private Integer almacenId;

    private Integer usuarioId;
}
