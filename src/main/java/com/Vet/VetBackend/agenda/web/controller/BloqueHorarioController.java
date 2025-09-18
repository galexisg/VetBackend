package com.Vet.VetBackend.agenda.web.controller;

import com.Vet.VetBackend.agenda.app.services.IBloqueHorarioService;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioModificarReq;
import com.Vet.VetBackend.agenda.web.dto.BloqueHorarioSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bloques-horario")
@RequiredArgsConstructor
public class BloqueHorarioController {

    private final IBloqueHorarioService service;

    @GetMapping("/activos")
    public List<BloqueHorarioSalidaRes> listar() {
        return service.listar();
    }

    @GetMapping("/inactivos")
    public List<BloqueHorarioSalidaRes> listarInactivos() {
        return service.listarInactivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloqueHorarioSalidaRes> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/crear")
    public BloqueHorarioSalidaRes guardar(@RequestBody BloqueHorarioGuardarReq dto) {
        return service.guardar(dto);
    }

    @PutMapping("/{id}/editar")
    public BloqueHorarioSalidaRes modificar(@PathVariable Integer id, @RequestBody BloqueHorarioModificarReq dto) {
        dto.setId(id);
        return service.modificar(dto);
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        service.activar(id);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "BloqueHorario activado correctamente");
        response.put("id", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "BloqueHorario desactivado correctamente");
        response.put("id", id);

        return ResponseEntity.ok(response);
    }
}