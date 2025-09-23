package com.Vet.VetBackend.diagnostico.web.controller;

import com.Vet.VetBackend.diagnostico.app.services.DiagnosticoService;
import com.Vet.VetBackend.diagnostico.web.dto.DiagnosticoDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas/{citaId}/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    @GetMapping
    public List<DiagnosticoDto> listar(@PathVariable Long citaId) {
        return diagnosticoService.listarPorCita(citaId);
    }

    @PostMapping
    public DiagnosticoDto crear(@PathVariable Long citaId, @RequestBody DiagnosticoDto dto) {
        return diagnosticoService.crear(citaId, dto);
    }

    @PutMapping("/{id}")
    public DiagnosticoDto editar(@PathVariable Long id, @RequestBody DiagnosticoDto dto) {
        return diagnosticoService.editar(id, dto);
    }

    @PatchMapping("/{id}/estado")
    public DiagnosticoDto cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
        return diagnosticoService.cambiarEstado(id, activo);
    }
}