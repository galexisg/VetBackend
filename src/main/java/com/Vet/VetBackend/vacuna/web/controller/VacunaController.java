package com.Vet.VetBackend.vacuna.web.controller;

import com.Vet.VetBackend.vacuna.domain.Vacuna;
import com.Vet.VetBackend.vacuna.app.services.VacunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacunas")
@RequiredArgsConstructor
@Tag(name = "Vacunas", description = "API para gestión de vacunas")
public class VacunaController { // Cambié el nombre a PascalCase

    private final VacunaService vacunaService;

    @GetMapping
    @Operation(summary = "Listar todas las vacunas")
    public ResponseEntity<List<Vacuna>> listar(@RequestParam(required = false) String q) {
        List<Vacuna> vacunas = vacunaService.listar(q);
        return ResponseEntity.ok(vacunas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vacuna por ID")
    public ResponseEntity<Vacuna> obtener(@PathVariable Integer id) {
        Vacuna vacuna = vacunaService.obtener(id);
        return ResponseEntity.ok(vacuna);
    }

    @PostMapping
    @Operation(summary = "Crear nueva vacuna")
    public ResponseEntity<Vacuna> crear(@RequestBody Vacuna vacuna) {
        Vacuna creada = vacunaService.crear(vacuna);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar vacuna existente")
    public ResponseEntity<Vacuna> actualizar(@PathVariable Integer id, @RequestBody Vacuna vacuna) {
        Vacuna actualizada = vacunaService.actualizar(id, vacuna);
        return ResponseEntity.ok(actualizada);
    }
<<<<<<< HEAD

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vacuna")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vacunaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
=======
>>>>>>> 47365c0346c830747f44f23869758f9200ebfd32
}
