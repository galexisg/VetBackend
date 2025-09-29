package com.Vet.VetBackend.motivocitas.web.controller;


import com.Vet.VetBackend.motivocitas.web.dto.MotivoCitaRes;
import com.Vet.VetBackend.motivocitas.web.dto.MotivoCitaReq;
import com.Vet.VetBackend.motivocitas.app.services.MotivoCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/motivocitas")
public class MotivoCitaController {

    @Autowired
    private MotivoCitaService motivoService;

    @GetMapping
    public ResponseEntity<?> listarActivos() {
        try {
            List<MotivoCitaRes> motivos = motivoService.listarActivos();
            return ResponseEntity.ok(motivos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MotivoCitaReq request) {
        try {
            MotivoCitaRes motivoCreado = motivoService.crear(request);
            return ResponseEntity.ok(motivoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody MotivoCitaReq request) {
        try {
            Optional<MotivoCitaRes> resultado = motivoService.editar(id, request);
            if (resultado.isPresent()) {
                return ResponseEntity.ok(resultado.get());
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Motivo no encontrado"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        try {
            Optional<MotivoCitaRes> resultado = motivoService.desactivar(id);
            if (resultado.isPresent()) {
                return ResponseEntity.ok(resultado.get());
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Motivo no encontrado"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        try {
            Optional<MotivoCitaRes> resultado = motivoService.activar(id);
            if (resultado.isPresent()) {
                return ResponseEntity.ok(resultado.get());
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Motivo no encontrado"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}