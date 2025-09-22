package com.Vet.VetBackend.consulta.web.controller;

import com.Vet.VetBackend.consulta.app.services.DiagnosticoService;
import com.Vet.VetBackend.consulta.web.dto.DiagnosticoDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    @PostMapping
    public CompletableFuture<DiagnosticoDto> crear(@RequestBody DiagnosticoDto dto) {
        return diagnosticoService.crearDiagnostico(dto);
    }

    @PutMapping("/{id}")
    public CompletableFuture<DiagnosticoDto> actualizar(@PathVariable Long id, @RequestBody DiagnosticoDto dto) {
        return diagnosticoService.actualizarDiagnostico(id, dto);
    }

    @GetMapping("/{id}")
    public CompletableFuture<DiagnosticoDto> obtenerPorId(@PathVariable Long id) {
        return diagnosticoService.obtenerPorId(id);
    }

    @GetMapping
    public CompletableFuture<List<DiagnosticoDto>> obtenerTodos() {
        return diagnosticoService.obtenerTodos();
    }
}
