package com.Vet.VetBackend.vacuna.web.controller;

import com.Vet.VetBackend.vacuna.domain.Vacuna;
import com.Vet.VetBackend.vacuna.app.services.VacunaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacunas")
@RequiredArgsConstructor

public class VacunaController { // Cambié el nombre a PascalCase

    private final VacunaService vacunaService;

    @GetMapping
    public ResponseEntity<List<Vacuna>> listar(@RequestParam(required = false) String q) {
        List<Vacuna> vacunas = vacunaService.listar(q);
        return ResponseEntity.ok(vacunas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacuna> obtener(@PathVariable Integer id) {
        Vacuna vacuna = vacunaService.obtener(id);
        return ResponseEntity.ok(vacuna);
    }

    @PostMapping
    public ResponseEntity<Vacuna> crear(@RequestBody Vacuna vacuna) {
        Vacuna creada = vacunaService.crear(vacuna);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacuna> actualizar(@PathVariable Integer id, @RequestBody Vacuna vacuna) {
        Vacuna actualizada = vacunaService.actualizar(id, vacuna);
        return ResponseEntity.ok(actualizada);
    }
}
