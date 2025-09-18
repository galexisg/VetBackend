package com.Vet.VetBackend.clinica.web.controller;

import com.Vet.VetBackend.clinica.app.services.HistorialService;
import com.Vet.VetBackend.clinica.web.dto.HistorialDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/historiales")
public class HistorialController {
    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @PostMapping
    public CompletableFuture<HistorialDto> crear(@RequestBody HistorialDto historialDTO) {
        return historialService.crearHistorial(historialDTO);
    }

    @PutMapping("/{id}")
    public CompletableFuture<HistorialDto> actualizar(@PathVariable Long id, @RequestBody HistorialDto historialDTO) {
        return historialService.actualizarHistorial(id, historialDTO);
    }

    @GetMapping
    public CompletableFuture<List<HistorialDto>> obtenerTodos() {
        return historialService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public CompletableFuture<HistorialDto> obtenerPorId(@PathVariable Long id) {
        return historialService.obtenerPorId(id);
    }

    @GetMapping("/mascota/{mascotaId}")
    public CompletableFuture<List<HistorialDto>> obtenerPorMascota(@PathVariable Long mascotaId) {
        return historialService.obtenerHistorialesPorMascota(mascotaId);
    }

}
