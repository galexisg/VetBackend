package com.Vet.VetBackend.consulta.web.controller;

import com.Vet.VetBackend.consulta.app.services.ConsultaService;
import com.Vet.VetBackend.consulta.web.dto.ConsultaDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public CompletableFuture<ConsultaDto> crear(@RequestBody ConsultaDto dto) {
        return consultaService.crearConsulta(dto);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ConsultaDto> actualizar(@PathVariable Long id, @RequestBody ConsultaDto dto) {
        return consultaService.actualizarConsulta(id, dto);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ConsultaDto> obtenerPorId(@PathVariable Long id) {
        return consultaService.obtenerPorId(id);
    }

    @GetMapping
    public CompletableFuture<List<ConsultaDto>> obtenerTodas() {
        return consultaService.obtenerTodas();
    }

    @GetMapping("/historial/{historialId}")
    public CompletableFuture<List<ConsultaDto>> obtenerPorHistorial(@PathVariable Long historialId) {
        return consultaService.obtenerPorHistorial(historialId);
    }
}
