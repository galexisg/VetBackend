package com.Vet.VetBackend.vacuna.app.services;

import com.Vet.VetBackend.vacuna.domain.Vacuna;

import java.util.List;


public interface VacunaService {
    List<Vacuna> listar(String q);
    Vacuna obtener(Integer id);
    Vacuna crear(Vacuna v);
    Vacuna actualizar(Integer id, Vacuna v);
    Vacuna estado(Integer id, boolean habilitar);



}
