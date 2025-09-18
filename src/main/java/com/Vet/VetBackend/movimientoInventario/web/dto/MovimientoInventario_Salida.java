package com.Vet.VetBackend.movimientoInventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class MovimientoInventario_Salida implements Serializable {
    private Integer id;

    private Date fecha;

    private String tipo;

    private String observacion;

//    private Almacen_Salida almacen;

//    private UsuarioSalida usuario;
}
