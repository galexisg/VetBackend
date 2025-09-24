package com.Vet.VetBackend.especie.web.controller;


import com.Vet.VetBackend.especie.app.services.EspecieService;
import com.Vet.VetBackend.especie.web.dto.EspecieActualizarReq;
import com.Vet.VetBackend.especie.web.dto.EspecieGuardarReq;
import com.Vet.VetBackend.especie.web.dto.EspecieRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especies")
@RequiredArgsConstructor
public class EspecieController {

    private final EspecieService especieService;

    @PostMapping
    public ResponseEntity<EspecieRes> guardar(@RequestBody EspecieGuardarReq req) {
        return ResponseEntity.ok(especieService.guardar(req));
    }

    @PutMapping
    public ResponseEntity<EspecieRes> actualizar(@RequestBody EspecieActualizarReq req) {
        return ResponseEntity.ok(especieService.actualizar(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Byte id) {
        especieService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieRes> buscarPorId(@PathVariable Byte id) {
        return ResponseEntity.ok(especieService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EspecieRes>> listar() {
        return ResponseEntity.ok(especieService.listar());
    }
}
