package com.Vet.VetBackend.citas.app.services;

import com.Vet.VetBackend.citas.web.dto.CitaResponseDTO;
import com.Vet.VetBackend.citas.web.dto.CitaRequestDTO;

import java.util.List;

public interface CitaService {
    CitaResponseDTO crear(CitaRequestDTO dto);
    CitaResponseDTO actualizar(Long id, CitaRequestDTO dto);
    CitaResponseDTO obtenerPorId(Long id);
    List<CitaResponseDTO> listarTodos();
}