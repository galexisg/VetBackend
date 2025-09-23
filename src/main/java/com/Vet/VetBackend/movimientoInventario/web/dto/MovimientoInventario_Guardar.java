package com.Vet.VetBackend.movimientoInventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoInventario_Guardar implements Serializable {
    private LocalDateTime fecha;
    private String observacion;
    private String tipo;
    private Integer almacenId;
    private Integer usuarioId;
}
