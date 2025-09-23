package com.Vet.VetBackend.diagnostico.app.services;

import com.Vet.VetBackend.diagnostico.web.dto.DiagnosticoDto;

import java.util.List;

public interface DiagnosticoService {
    List<DiagnosticoDto> listarPorCita(Long citaId);

    DiagnosticoDto crear(Long citaId, DiagnosticoDto dto);

    DiagnosticoDto editar(Long id, DiagnosticoDto dto);

    DiagnosticoDto cambiarEstado(Long id, boolean activo);
}
