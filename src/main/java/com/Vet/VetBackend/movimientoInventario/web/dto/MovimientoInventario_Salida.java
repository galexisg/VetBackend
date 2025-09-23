package com.Vet.VetBackend.movimientoInventario.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoInventario_Salida implements Serializable {
    private Integer id;
    private LocalDateTime fecha;
    private String tipo;
    private String observacion;
    private Integer almacenId;
    private Integer usuarioId; // ✅ Campo plano, compatible con migración
}
