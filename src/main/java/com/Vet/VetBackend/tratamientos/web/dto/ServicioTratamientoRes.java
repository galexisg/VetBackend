package com.Vet.VetBackend.tratamientos.web.dto;


import com.Vet.VetBackend.tratamientos.domain.ServicioTratamiento;
import lombok.Data;

@Data
public class ServicioTratamientoRes {
    private Long id;           // ← id (doc)
    private Long servicioId;
    private Long tratamientoId;

    public static ServicioTratamientoRes fromEntity(ServicioTratamiento st) {
        ServicioTratamientoRes res = new ServicioTratamientoRes();
        res.setId(st.getId());
        res.setServicioId(st.getServicioId());
        res.setTratamientoId(st.getTratamientoId());
        return res;
    }
}

