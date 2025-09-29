package com.Vet.VetBackend.tratamientos.web.dto;


import com.Vet.VetBackend.tratamientos.domain.TratamientoAplicado;
import lombok.Data;

@Data
public class TratamientoAplicadoRes {
    private Long id;           // ← id
    private Long citaId;
    private Long tratamientoId;
    private Long veterinarioId;
    private String estado;
    private String observaciones;

    public static TratamientoAplicadoRes fromEntity(TratamientoAplicado ta) {
        TratamientoAplicadoRes res = new TratamientoAplicadoRes();
        res.setId(ta.getId());
        res.setCitaId(ta.getCitaId());
        res.setTratamientoId(ta.getTratamientoId());
        res.setVeterinarioId(ta.getVeterinarioId());
        res.setEstado(ta.getEstado());
        res.setObservaciones(ta.getObservaciones());
        return res;
    }
}

