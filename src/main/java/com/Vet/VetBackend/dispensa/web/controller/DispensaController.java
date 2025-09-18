package com.Vet.VetBackend.dispensa.web.controller;

import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Actualizar;
import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Guardar;
import com.Vet.VetBackend.dispensa.web.dto.Dispensa_Salida;
import com.Vet.VetBackend.dispensa.app.services.IDispensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dispensas")
public class DispensaController {

    @Autowired
    private IDispensaService service;

    @GetMapping
    public ResponseEntity<Page<Dispensa_Salida>> obtenerTodasPaginadas(Pageable pageable) {
        Page<Dispensa_Salida> resultado = service.obtenerTodasPaginadas(pageable);
        return resultado.hasContent() ? ResponseEntity.ok(resultado) : ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Dispensa_Salida>> obtenerTodas() {
        List<Dispensa_Salida> resultado = service.obtenerTodas();
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispensa_Salida> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Dispensa_Salida> crear(@RequestBody Dispensa_Guardar dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispensa_Salida> editar(@PathVariable Integer id, @RequestBody Dispensa_Actualizar dto) {
        return ResponseEntity.ok(service.editar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prescripcion/{prescripcionDetalleId}")
    public ResponseEntity<List<Dispensa_Salida>> obtenerPorPrescripcion(@PathVariable Integer prescripcionDetalleId) {
        List<Dispensa_Salida> resultado = service.obtenerPorPrescripcion(prescripcionDetalleId);
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @GetMapping("/almacen/{almacenId}")
    public ResponseEntity<List<Dispensa_Salida>> obtenerPorAlmacen(@PathVariable Integer almacenId) {
        List<Dispensa_Salida> resultado = service.obtenerPorAlmacen(almacenId);
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Dispensa_Salida>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        List<Dispensa_Salida> resultado = service.obtenerPorFecha(fecha);
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }
}
