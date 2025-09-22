package com.Vet.VetBackend.consulta.app.services;

import com.Vet.VetBackend.consulta.web.dto.ConsultaDiagnosticoDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ConsultaDiagnosticoService {
    CompletableFuture<ConsultaDiagnosticoDto> crear(ConsultaDiagnosticoDto dto);
    CompletableFuture<List<ConsultaDiagnosticoDto>> obtenerPorConsulta(Long consultaId);
    CompletableFuture<Void> eliminar(Long id);
}
