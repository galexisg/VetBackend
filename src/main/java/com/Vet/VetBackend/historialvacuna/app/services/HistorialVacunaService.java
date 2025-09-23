package com.Vet.VetBackend.historialvacuna.app.services;

import com.Vet.VetBackend.historialvacuna.web.dto.CreateHistorialVacunaDTO;
import com.Vet.VetBackend.historialvacuna.web.dto.HistorialVacunaDTO;

import java.util.List;

public interface HistorialVacunaService {

    HistorialVacunaDTO guardar(CreateHistorialVacunaDTO dto);

    List<HistorialVacunaDTO> listarPorMascota(Integer mascotaId);

}