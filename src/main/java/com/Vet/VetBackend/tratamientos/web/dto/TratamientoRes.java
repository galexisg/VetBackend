package com.Vet.VetBackend.tratamientos.web.dto;


import com.Vet.VetBackend.tratamientos.domain.Tratamiento;
import lombok.Data;

@Data
public class TratamientoRes {
    private Long id;              // ← id (doc)
    private String nombre;
    private String descripcion;
    private Integer duracionDias;
    private String frecuencia;
    private String via;
    private Boolean activo;

    public static TratamientoRes fromEntity(Tratamiento t) {
        TratamientoRes res = new TratamientoRes();
        res.setId(t.getId());
        res.setNombre(t.getNombre());
        res.setDescripcion(t.getDescripcion());
        res.setDuracionDias(t.getDuracionDias());
        res.setFrecuencia(t.getFrecuencia());
        res.setVia(t.getVia());
        res.setActivo(t.getActivo());
        return res;
    }
}
