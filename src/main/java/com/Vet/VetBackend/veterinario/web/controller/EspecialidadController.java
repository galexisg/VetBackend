package com.Vet.VetBackend.veterinario.web.controller;

import com.Vet.VetBackend.veterinario.app.services.IEspecialidadService;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.EspecialidadSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final IEspecialidadService service;

    @GetMapping("/activos")
    public List<EspecialidadSalidaRes> listar() {
        return service.listar();
    }

    @GetMapping("/inactivos")
    public List<EspecialidadSalidaRes> listarInactivos() {
        return service.listarInactivos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadSalidaRes> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/Crear")
    public EspecialidadSalidaRes guardar(@RequestBody EspecialidadGuardarReq dto) {
        return service.guardar(dto);
    }

    @PutMapping("/{id}/editar")
    public EspecialidadSalidaRes modificar(@PathVariable Integer id, @RequestBody EspecialidadModificarReq dto) {
        dto.setEspecialidadId(id); // aseguramos que usemos el ID de la URL
        return service.modificar(dto);
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        service.activar(id);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Especialidad activada correctamente");
        response.put("id", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Especialidad desactivada correctamente");
        response.put("id", id);

        return ResponseEntity.ok(response);
    }

}