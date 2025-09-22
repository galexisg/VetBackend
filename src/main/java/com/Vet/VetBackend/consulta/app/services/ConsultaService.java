package com.Vet.VetBackend.consulta.app.services;

import com.Vet.VetBackend.consulta.web.dto.ConsultaDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ConsultaService {
    CompletableFuture<ConsultaDto> crearConsulta(ConsultaDto dto);
    CompletableFuture<ConsultaDto> actualizarConsulta(Long id,ConsultaDto dto);
    CompletableFuture<ConsultaDto> obtenerPorId(Long id);
    CompletableFuture<List<ConsultaDto>> obtenerTodas();
    CompletableFuture<List<ConsultaDto>> obtenerPorHistorial(Long historialId);
}
