package com.Vet.VetBackend.estadocita.web.controller;

import com.Vet.VetBackend.estadocita.web.dto.EstadoGuardar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoModificar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoSalida;
import com.Vet.VetBackend.estadocita.app.services.IEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas/estados")
public class EstadoCitaController {

    @Autowired
    private IEstadoService estadoService;

    // 🔹 GET paginado
    @GetMapping
    public ResponseEntity<Page<EstadoSalida>> mostrarTodosPaginados(Pageable pageable) {
        Page<EstadoSalida> estados = estadoService.obtenerTodosPaginados(pageable);
        if (estados.hasContent()) {
            return ResponseEntity.ok(estados);
        }
        return ResponseEntity.noContent().build();
    }

    // 🔹 GET lista completa
    @GetMapping("/lista")
    public ResponseEntity<List<EstadoSalida>> mostrarTodos() {
        List<EstadoSalida> estados = estadoService.obtenerTodos();
        if (!estados.isEmpty()) {
            return ResponseEntity.ok(estados);
        }
        return ResponseEntity.noContent().build();
    }

    // 🔹 GET por id
    @GetMapping("/{id}")
    public ResponseEntity<EstadoSalida> buscarPorId(@PathVariable Integer id) {
        try {
            EstadoSalida estado = estadoService.obtenerPorId(id);
            return ResponseEntity.ok(estado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 POST crear
    @PostMapping
    public ResponseEntity<EstadoSalida> crear(@RequestBody EstadoGuardar estadoGuardar) {
        EstadoSalida estado = estadoService.crear(estadoGuardar);
        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    // 🔹 PUT editar
    @PutMapping("/{id}")
    public ResponseEntity<EstadoSalida> editar(@PathVariable Integer id, @RequestBody EstadoModificar estadoModificar) {
        if (!id.equals(estadoModificar.getId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            EstadoSalida estado = estadoService.editar(estadoModificar);
            return ResponseEntity.ok(estado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
