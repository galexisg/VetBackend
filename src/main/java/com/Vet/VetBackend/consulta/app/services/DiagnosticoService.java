package com.Vet.VetBackend.consulta.app.services;

import com.Vet.VetBackend.consulta.web.dto.DiagnosticoDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DiagnosticoService {
    CompletableFuture<DiagnosticoDto> crearDiagnostico(DiagnosticoDto dto);
    CompletableFuture<DiagnosticoDto> actualizarDiagnostico(Long id, DiagnosticoDto dto);
    CompletableFuture<DiagnosticoDto> obtenerPorId(Long id);
    CompletableFuture<List<DiagnosticoDto>> obtenerTodos();
}
