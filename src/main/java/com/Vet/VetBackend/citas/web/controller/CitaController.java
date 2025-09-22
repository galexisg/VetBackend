package com.Vet.VetBackend.citas.web.controller;

import com.Vet.VetBackend.citas.app.services.CitaService;
import com.Vet.VetBackend.citas.web.dto.CitaRequestDTO;
import com.Vet.VetBackend.citas.web.dto.CitaResponseDTO;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> crear(@Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO created = citaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizar(@PathVariable("id") Long id, @Valid @RequestBody CitaRequestDTO dto) {
        CitaResponseDTO updated = citaService.actualizar(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable("id") Long id) {
        CitaResponseDTO dto = citaService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listarTodos() {
        List<CitaResponseDTO> list = citaService.listarTodos();
        return ResponseEntity.ok(list);
    }
}
