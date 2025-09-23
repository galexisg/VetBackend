package com.Vet.VetBackend.Medicamento.web.controller;

import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoGuardar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoModificar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoSalida;
import com.Vet.VetBackend.Medicamento.app.services.IMedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private IMedicamentoService medicamentoService;

    @GetMapping
    public ResponseEntity<Page<MedicamentoSalida>> obtenerTodosPaginados(Pageable pageable) {
        Page<MedicamentoSalida> medicamentos = medicamentoService.obtenerTodosPaginados(pageable);
        return medicamentos.hasContent()
                ? ResponseEntity.ok(medicamentos)
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<MedicamentoSalida>> obtenerTodos() {
        List<MedicamentoSalida> medicamentos = medicamentoService.obtenerTodos();
        return medicamentos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(medicamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoSalida> obtenerPorId(@PathVariable Integer id) {
        MedicamentoSalida salida = medicamentoService.obtenerPorId(id);
        return salida != null
                ? ResponseEntity.ok(salida)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MedicamentoSalida> crear(@RequestBody MedicamentoGuardar dto) {
        MedicamentoSalida salida = medicamentoService.crear(dto);
        return ResponseEntity.ok(salida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoSalida> editar(@PathVariable Integer id, @RequestBody MedicamentoModificar dto) {
        dto.setId(id); // Asegura que el ID del path se use
        MedicamentoSalida salida = medicamentoService.editar(dto);
        return ResponseEntity.ok(salida);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        medicamentoService.eliminarPorId(id);
        return ResponseEntity.ok("Medicamento eliminado correctamente");
    }
}
