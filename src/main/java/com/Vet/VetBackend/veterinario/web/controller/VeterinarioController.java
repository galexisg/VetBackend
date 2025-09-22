package com.Vet.VetBackend.veterinario.web.controller;

import com.Vet.VetBackend.veterinario.app.services.IVeterinarioService;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final IVeterinarioService servicio;

    // Guardar nuevo veterinario con especialidades
    @PostMapping
    public VeterinarioSalidaRes guardar(@RequestBody VeterinarioGuardarReq dto) {
        return servicio.guardar(dto);
    }

    // Listar todos (activos e inactivos)
    @GetMapping
    public List<VeterinarioSalidaRes> listar() {
        return servicio.listar();
    }

    // Listar solo activos
    @GetMapping("/activos")
    public List<VeterinarioSalidaRes> listarActivos() {
        return servicio.listarActivos();
    }

    // Listar solo inactivos
    @GetMapping("/inactivos")
    public List<VeterinarioSalidaRes> listarInactivos() {
        return servicio.listarInactivos();
    }

    // Modificar veterinario (solo datos básicos, no especialidades)
    @PutMapping("/{id}")
    public VeterinarioSalidaRes modificar(@PathVariable int id, @RequestBody VeterinarioModificarReq dto) {
        dto.setId(id); // asegura que se use el ID del path
        return servicio.modificar(dto);
    }

    // Inactivar veterinario (soft delete)
    @PutMapping("/{id}/inactivar")
    public ResponseEntity<String> inactivar(@PathVariable int id) {
        servicio.inactivar(id);
        return ResponseEntity.ok("Veterinario marcado como inactivo con éxito");
    }

    // Activar veterinario
    @PutMapping("/{id}/activar")
    public ResponseEntity<String> activar(@PathVariable int id) {
        servicio.activar(id);
        return ResponseEntity.ok("Veterinario activado con éxito");
    }
}
