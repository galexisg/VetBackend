package com.Vet.VetBackend.movimientoInventario.web.dto;

import com.Vet.VetBackend.movimientoInventario.domain.MovimientoInventario;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class MovimientoInventario_Guardar implements Serializable {

    private LocalDateTime fecha;

    private String observacion;

    private Integer almacenId;

    //private Integer usuarioId;
}