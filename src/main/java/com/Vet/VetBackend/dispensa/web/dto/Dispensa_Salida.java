package com.Vet.VetBackend.dispensa.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

@Getter
@Setter
public class Dispensa_Salida implements Serializable {
    private Integer id;
    private Integer prescripcionDetalleId;
    private Integer almacenId;
    private Integer loteId;
    private BigDecimal cantidad;
    private LocalDateTime fecha;
    private Integer usuarioId;
}
