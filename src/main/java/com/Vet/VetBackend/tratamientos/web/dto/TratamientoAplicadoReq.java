package com.Vet.VetBackend.tratamientos.web.dto;

import lombok.Data;

@Data
public class TratamientoAplicadoReq {
    private Long citaId;
    private Long tratamientoId;
    private Long veterinarioId;
    private String observaciones;
}

