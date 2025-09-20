package com.Vet.VetBackend.historialvacuna.app.web.controller;

import com.Vet.VetBackend.historialvacuna.app.services.HistorialVacunaService;
import com.Vet.VetBackend.historialvacuna.app.web.dto.CreateHistorialVacunaDTO;
import com.Vet.VetBackend.historialvacuna.app.web.dto.HistorialVacunaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-vacunas")
@RequiredArgsConstructor
public class HistorialVacunaController {

    private final HistorialVacunaService historialVacunaService;

    @PostMapping
    public ResponseEntity<HistorialVacunaDTO> guardar(@RequestBody CreateHistorialVacunaDTO dto) {
        return ResponseEntity.ok(historialVacunaService.guardar(dto));
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<HistorialVacunaDTO>> listarPorMascota(@PathVariable Integer mascotaId) {
        return ResponseEntity.ok(historialVacunaService.listarPorMascota(mascotaId));
    }
}