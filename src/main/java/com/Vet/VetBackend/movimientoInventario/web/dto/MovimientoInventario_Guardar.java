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
    private String tipo; // agregado para que el usuario pueda definir ENTRADA o SALIDA
}
