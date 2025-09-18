package com.Vet.VetBackend.agenda.web.controller;


import com.Vet.VetBackend.agenda.app.services.IDiaService;
import com.Vet.VetBackend.agenda.web.dto.DiaGuardarReq;
import com.Vet.VetBackend.agenda.web.dto.DiaModificarReq;
import com.Vet.VetBackend.agenda.web.dto.DiaSalidaRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dias")
public class DiaController {

    @Autowired
    private IDiaService diaService;

    @GetMapping
    public ResponseEntity<List<DiaSalidaRes>> listarDias() {
        return ResponseEntity.ok(diaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaSalidaRes> buscarDiaPorId(@PathVariable int id) {
        return ResponseEntity.ok(diaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DiaSalidaRes> crearDia(@RequestBody DiaGuardarReq diaDTO) {
        return new ResponseEntity<>(diaService.save(diaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaSalidaRes> actualizarDia(@PathVariable int id, @RequestBody DiaModificarReq diaDTO) {
        return ResponseEntity.ok(diaService.update(id, diaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDia(@PathVariable int id) {
        diaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}