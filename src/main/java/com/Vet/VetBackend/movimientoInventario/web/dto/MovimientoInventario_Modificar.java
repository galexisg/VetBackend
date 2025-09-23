package com.Vet.VetBackend.movimientoInventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoInventario_Modificar implements Serializable {
    private Integer id;
    private String tipo;
    private LocalDateTime fecha;
    private String observacion;
    private Integer almacenId;
    private Integer usuarioId;
}
