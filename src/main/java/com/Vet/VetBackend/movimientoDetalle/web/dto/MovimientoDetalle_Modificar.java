package com.Vet.VetBackend.movimientoDetalle.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoDetalle_Modificar implements Serializable {
    private Integer id;
    private double cantidad;
    private double costoUnitario;
    private LocalDateTime fecha;

    private Integer movimientoInventarioId;
    private Integer medicamentoId;
    private Integer loteMedicamentoId;
    private Integer almacenId;
    private Integer usuarioId; // Nuevo campo
}
