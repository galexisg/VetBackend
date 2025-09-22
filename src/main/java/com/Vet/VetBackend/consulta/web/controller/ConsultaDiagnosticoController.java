package com.Vet.VetBackend.consulta.web.controller;

import com.Vet.VetBackend.consulta.app.services.ConsultaDiagnosticoService;
import com.Vet.VetBackend.consulta.web.dto.ConsultaDiagnosticoDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/consulta-diagnosticos")
public class ConsultaDiagnosticoController {

    private final ConsultaDiagnosticoService service;

    public ConsultaDiagnosticoController(ConsultaDiagnosticoService service) {
        this.service = service;
    }

    @PostMapping
    public CompletableFuture<ConsultaDiagnosticoDto> crear(@RequestBody ConsultaDiagnosticoDto dto) {
        return service.crear(dto);
    }

    @GetMapping("/consulta/{consultaId}")
    public CompletableFuture<List<ConsultaDiagnosticoDto>> obtenerPorConsulta(@PathVariable Long consultaId) {
        return service.obtenerPorConsulta(consultaId);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}