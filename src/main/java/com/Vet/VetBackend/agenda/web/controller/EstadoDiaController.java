package com.Vet.VetBackend.agenda.web.controller;

import com.Vet.VetBackend.agenda.app.services.IEstadoDiaService;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaModificarReq;
import com.Vet.VetBackend.agenda.web.dto.EstadoDiaSalidaRes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estados-dia")
public class EstadoDiaController {

    @Autowired
    private IEstadoDiaService estadoDiaService;

    @GetMapping
    public ResponseEntity<List<EstadoDiaSalidaRes>> listarEstados() {
        return ResponseEntity.ok(estadoDiaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDiaSalidaRes> buscarEstadoPorId(@PathVariable int id) {
        return ResponseEntity.ok(estadoDiaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EstadoDiaSalidaRes> crearEstado(@Valid @RequestBody EstadoDiaGuardarReq estadoDTO) {
        return new ResponseEntity<>(estadoDiaService.save(estadoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDiaSalidaRes> actualizarEstado(@PathVariable int id, @Valid @RequestBody EstadoDiaModificarReq estadoDTO) {
        return ResponseEntity.ok(estadoDiaService.update(id, estadoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstado(@PathVariable int id) {
        estadoDiaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}