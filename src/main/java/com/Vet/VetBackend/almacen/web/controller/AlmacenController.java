package com.Vet.VetBackend.almacen.web.controller;

import com.Vet.VetBackend.almacen.app.services.IAlmacenService;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Guardar;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Actualizar;
import com.Vet.VetBackend.almacen.web.dto.Almacen_Salida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {

    @Autowired
    private IAlmacenService service;

    @GetMapping
    public ResponseEntity<Page<Almacen_Salida>> obtenerTodosPaginados(Pageable pageable) {
        Page<Almacen_Salida> resultado = service.obtenerTodosPaginados(pageable);
        return resultado.hasContent() ? ResponseEntity.ok(resultado) : ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Almacen_Salida>> obtenerTodos() {
        List<Almacen_Salida> resultado = service.obtenerTodos();
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Almacen_Salida> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Almacen_Salida> crear(@RequestBody Almacen_Guardar dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Almacen_Salida> editar(@PathVariable Integer id, @RequestBody Almacen_Actualizar dto) {
        return ResponseEntity.ok(service.editar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
