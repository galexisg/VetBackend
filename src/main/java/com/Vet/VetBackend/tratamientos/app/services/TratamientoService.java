package com.Vet.VetBackend.tratamientos.app.services;


import com.Vet.VetBackend.tratamientos.web.dto.TratamientoReq;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoRes;

import java.util.List;

public interface TratamientoService {
    List<TratamientoRes> listar();
    TratamientoRes obtenerPorId(Long id);
    TratamientoRes crear(TratamientoReq req);
    TratamientoRes actualizar(Long id, TratamientoReq req);
    void activarInactivar(Long id, boolean activo);
}


