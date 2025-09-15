package com.Vet.VetBackend.tratamientos.web.dto;

import com.Vet.VetBackend.tratamientos.domain.Tratamiento;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TratamientoRes {
    private Long id;
    private String nombre;
    private String descripcion;

    // NUEVO
    private BigDecimal precioSugerido;

    private Integer duracionDias;
    private String frecuencia;
    private String via;
    private Boolean activo;

    public static TratamientoRes fromEntity(Tratamiento t) {
        TratamientoRes res = new TratamientoRes();
        res.setId(t.getId());
        res.setNombre(t.getNombre());
        res.setDescripcion(t.getDescripcion());
        res.setPrecioSugerido(t.getPrecioSugerido()); // ← mapeo del precio
        res.setDuracionDias(t.getDuracionDias());
        res.setFrecuencia(t.getFrecuencia());
        res.setVia(t.getVia());
        res.setActivo(Boolean.TRUE.equals(t.getActivo()));
        return res;
    }
}
