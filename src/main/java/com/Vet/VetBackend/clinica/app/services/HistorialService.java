package com.Vet.VetBackend.clinica.app.services;


import com.Vet.VetBackend.clinica.web.dto.HistorialDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HistorialService {
    CompletableFuture<HistorialDto> crearHistorial(HistorialDto historialDTO);
    CompletableFuture<List<HistorialDto>> obtenerTodos();
    CompletableFuture<HistorialDto> obtenerPorId(Long id);
    CompletableFuture<List<HistorialDto>> obtenerHistorialesPorMascota(Integer mascotaId);
}

