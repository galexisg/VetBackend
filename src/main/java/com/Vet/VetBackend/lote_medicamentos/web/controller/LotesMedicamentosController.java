package com.Vet.VetBackend.lote_medicamentos.web.controller;

import com.Vet.VetBackend.lote_medicamentos.app.services.ILotesMedicamentoService;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamento_Salida;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Actualizar;
import com.Vet.VetBackend.lote_medicamentos.web.dto.LoteMedicamentos_Guardar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Lotes_medicamentos")
public class LotesMedicamentosController {

    @Autowired
    private ILotesMedicamentoService lotesMedicamentoService;

    @GetMapping
    public ResponseEntity<Page<LoteMedicamento_Salida>> mostrartodosPaginados(Pageable pageable) {
        Page<LoteMedicamento_Salida> lotes = lotesMedicamentoService.obtenerTodosPaginados(pageable);
        return lotes.hasContent() ? ResponseEntity.ok(lotes) : ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<LoteMedicamento_Salida>> mostrarTodos() {
        List<LoteMedicamento_Salida> lotes = lotesMedicamentoService.obtenerTodos();
        return lotes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteMedicamento_Salida> buscarPorId(@PathVariable Integer id) {
        LoteMedicamento_Salida lote = lotesMedicamentoService.obtenerPorId(id);
        return lote != null ? ResponseEntity.ok(lote) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<LoteMedicamento_Salida> crear(@RequestBody LoteMedicamentos_Guardar dto) {
        LoteMedicamento_Salida creado = lotesMedicamentoService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoteMedicamento_Salida> editar(@PathVariable Integer id, @RequestBody LoteMedicamentos_Actualizar dto) {
        dto.setId(id); // ✅ sincroniza el ID del path con el DTO
        LoteMedicamento_Salida actualizado = lotesMedicamentoService.editar(dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        lotesMedicamentoService.eliminarPorId(id);
        return ResponseEntity.noContent().build(); // ✅ respuesta estándar para DELETE
    }

    @GetMapping("/Medicamento/{medicamentoId}")
    public ResponseEntity<List<LoteMedicamento_Salida>> obtenerPorMedicamento(@PathVariable Integer medicamentoId) {
        List<LoteMedicamento_Salida> resultado = lotesMedicamentoService.obtenerPorMedicamentoId(medicamentoId);
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<LoteMedicamento_Salida>> obtenerPorProveedor(@PathVariable Integer proveedorId) {
        List<LoteMedicamento_Salida> resultado = lotesMedicamentoService.obtenerPorProveedorId(proveedorId);
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @GetMapping("/proximos-a-vencer")
    public ResponseEntity<List<LoteMedicamento_Salida>> obtenerProximosAVencer() {
        List<LoteMedicamento_Salida> resultado = lotesMedicamentoService.obtenerLotesProximosAVencer();
        return resultado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }
}


//package Controladores;
//
//import INVENTARIO.Servicios.implementaciones.LotesMedicamentoService;
//import INVENTARIO.Servicios.interfaces.ILotesMedicamentoService;
//import INVENTARIO.dtos.LotesMedicamentos.LoteMedicamento_Salida;
//import INVENTARIO.dtos.LotesMedicamentos.LoteMedicamentos_Actualizar;
//import INVENTARIO.dtos.LotesMedicamentos.LoteMedicamentos_Guardar;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/Lotes_medicamentos")
//public class LotesMedicamentosController {
//    @Autowired
//    private ILotesMedicamentoService lotesMedicamentoService;
//
//    @GetMapping
//    public ResponseEntity<Page<LoteMedicamento_Salida>> mostrartodosPaginados(Pageable pageable){
//        Page<LoteMedicamento_Salida> lotes_medicamento = lotesMedicamentoService.obtenerTodosPaginados(pageable);
//        if (lotes_medicamento.hasContent()){
//            return ResponseEntity.ok(lotes_medicamento);
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/lista")
//    public ResponseEntity<List<LoteMedicamento_Salida>> mostrarTodos(){
//        List<LoteMedicamento_Salida> lotes_medicamento = lotesMedicamentoService.obtenerTodos();
//        if (!lotes_medicamento.isEmpty()){
//            return ResponseEntity.ok(lotes_medicamento);
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<LoteMedicamento_Salida> buscarPorId(@PathVariable Integer id){
//        LoteMedicamento_Salida lotes_medicamento = lotesMedicamentoService.obtenerPorId(id);
//        if (lotes_medicamento != null){
//            return ResponseEntity.ok(lotes_medicamento);
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    public ResponseEntity<LoteMedicamento_Salida> crear(@RequestBody LoteMedicamentos_Guardar loteMedicamentosGuardar){
//        LoteMedicamento_Salida lotes_medicamento = lotesMedicamentoService.crear(loteMedicamentosGuardar);
//        return ResponseEntity.ok(lotes_medicamento);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<LoteMedicamento_Salida> editar(@PathVariable Integer id, @RequestBody LoteMedicamentos_Actualizar loteMedicamentosActualizar){
//        LoteMedicamento_Salida lotes_medicamento = lotesMedicamentoService.editar(loteMedicamentosActualizar);
//        return ResponseEntity.ok(lotes_medicamento);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity eliminar(@PathVariable Integer id){
//        lotesMedicamentoService.eliminarPorId(id);
//        return ResponseEntity.ok("Lote eliminado correctamente");
//    }
//}
