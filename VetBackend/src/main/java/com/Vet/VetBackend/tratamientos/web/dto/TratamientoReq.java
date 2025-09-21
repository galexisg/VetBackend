package com.Vet.VetBackend.tratamientos.web.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TratamientoReq {
    private String nombre;
    private String descripcion;

    // NUEVO
    private BigDecimal precioSugerido;

    private Integer duracionDias;
    private String frecuencia;
    private String via;
}
