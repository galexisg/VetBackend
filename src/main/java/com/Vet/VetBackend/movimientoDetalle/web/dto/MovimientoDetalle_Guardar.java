package com.Vet.VetBackend.movimientoDetalle.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoDetalle_Guardar implements Serializable {
    private double cantidad;
    private double costoUnitario;

    private Integer movimientoInventarioId;
    private Integer medicamentoId;
    private Integer loteMedicamentoId; // corregido nombre

    private Integer almacenId;         //  faltaba
    private LocalDateTime fecha;      // faltaba
}
