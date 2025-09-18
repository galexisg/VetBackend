package com.Vet.VetBackend.agenda.web.controller;

import com.Vet.VetBackend.agenda.app.services.IDetalleHorarioVeterinarioService;
import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.DetalleHorarioVeterinarioSalidaRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detallehorarioveterinario")
public class DetalleHorarioVeterinarioController {

    @Autowired
    private IDetalleHorarioVeterinarioService detalleHorarioVeterinarioService;

    @GetMapping
    public ResponseEntity<List<DetalleHorarioVeterinarioSalidaRes>> listarTodos() {
        return ResponseEntity.ok(detalleHorarioVeterinarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleHorarioVeterinarioSalidaRes> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleHorarioVeterinarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DetalleHorarioVeterinarioSalidaRes> crear(@RequestBody DetalleHorarioVeterinarioGuardarReq detalleDTO) {
        return new ResponseEntity<>(detalleHorarioVeterinarioService.save(detalleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleHorarioVeterinarioSalidaRes> actualizar(@PathVariable Integer id, @RequestBody DetalleHorarioVeterinarioGuardarReq detalleDTO) {
        return ResponseEntity.ok(detalleHorarioVeterinarioService.update(id, detalleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        detalleHorarioVeterinarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}