package com.Vet.VetBackend.vacuna.web.controller;

import com.Vet.VetBackend.vacuna.domain.Vacuna;
import com.Vet.VetBackend.vacuna.app.services.VacunaService;
import com.Vet.VetBackend.vacuna.web.dto.VacunaRequestDTO;
import com.Vet.VetBackend.vacuna.web.dto.VacunaResponseDTO;
import com.Vet.VetBackend.vacuna.web.dto.VacunaEstadoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vacunas")
@RequiredArgsConstructor

public class VacunaController {

    private final VacunaService vacunaService;

    // 🔹 Listar todas las vacunas
    @GetMapping
    public ResponseEntity<List<VacunaResponseDTO>> listar(@RequestParam(required = false) String q) {
        List<VacunaResponseDTO> vacunas = vacunaService.listar(q).stream()
                .map(v -> new VacunaResponseDTO(v.getVacunaId(), v.getNombre(), v.getEstado()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(vacunas);
    }

    // 🔹 Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<VacunaResponseDTO> obtener(@PathVariable Integer id) {
        Vacuna v = vacunaService.obtener(id);
        VacunaResponseDTO dto = new VacunaResponseDTO(v.getVacunaId(), v.getNombre(), v.getEstado());
        return ResponseEntity.ok(dto);
    }

    // 🔹 Crear
    @PostMapping
    public ResponseEntity<VacunaResponseDTO> crear(@RequestBody @Valid VacunaRequestDTO dto) {
        Vacuna vacuna = new Vacuna();
        vacuna.setNombre(dto.getNombre());
        vacuna.setEstado(true);

        Vacuna creada = vacunaService.crear(vacuna);
        VacunaResponseDTO response = new VacunaResponseDTO(creada.getVacunaId(), creada.getNombre(), creada.getEstado());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 🔹 Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<VacunaResponseDTO> actualizar(@PathVariable Integer id,
                                                        @RequestBody @Valid VacunaRequestDTO dto) {
        Vacuna vacuna = new Vacuna();
        vacuna.setNombre(dto.getNombre());

        Vacuna actualizada = vacunaService.actualizar(id, vacuna);
        VacunaResponseDTO response = new VacunaResponseDTO(actualizada.getVacunaId(), actualizada.getNombre(), actualizada.getEstado());
        return ResponseEntity.ok(response);
    }

    // 🔹 Habilitar
    @PatchMapping("/habilitar/{id}")
    public ResponseEntity<VacunaEstadoDTO> habilitar(@PathVariable Integer id) {
        Vacuna vacuna = vacunaService.estado(id, true);
        return ResponseEntity.ok(new VacunaEstadoDTO(vacuna.getVacunaId(), vacuna.getEstado()));
    }

    // 🔹 Deshabilitar
    @PatchMapping("/deshabilitar/{id}")
    public ResponseEntity<VacunaEstadoDTO> deshabilitar(@PathVariable Integer id) {
        Vacuna vacuna = vacunaService.estado(id, false);
        return ResponseEntity.ok(new VacunaEstadoDTO(vacuna.getVacunaId(), vacuna.getEstado()));
    }
}
