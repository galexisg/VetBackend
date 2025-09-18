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
    public ResponseEntity<Page<MedicamentoSalida>> mostrarTodosPaginados(Pageable pageable) {
        Page<MedicamentoSalida> medicamentos = medicamentoService.obtenerTodosPaginados(pageable);
        if (medicamentos.hasContent()) {
            return ResponseEntity.ok(medicamentos);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<MedicamentoSalida>> mostrarTodos(){
        List<MedicamentoSalida> Medicamento = medicamentoService.obtenerTodos();
        if (!Medicamento.isEmpty()){
            return ResponseEntity.ok(Medicamento);
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoSalida> buscarPorId(@PathVariable Integer id){
        MedicamentoSalida Medicamento= medicamentoService.obtenerPorId(id);
        if(Medicamento != null){
            return ResponseEntity.ok(Medicamento);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MedicamentoSalida> crear(@RequestBody MedicamentoGuardar medicamentoGuardar){
        MedicamentoSalida Medicamento = medicamentoService.crear(medicamentoGuardar);
        return ResponseEntity.ok(Medicamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoSalida> editar(@PathVariable Integer id, @RequestBody MedicamentoModificar medicamentoModificar){
        MedicamentoSalida Medicamento = medicamentoService.editar(medicamentoModificar);
        return ResponseEntity.ok(Medicamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Integer id){
        medicamentoService.eliminarPorId(id);
        return ResponseEntity.ok().body("Medicamento eliminado correctamente");
    }


}


