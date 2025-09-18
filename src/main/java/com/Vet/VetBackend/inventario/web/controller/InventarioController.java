package com.Vet.VetBackend.inventario.web.controller;

import com.Vet.VetBackend.inventario.app.services.IInventarioService;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Guardar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Modificar;
import com.Vet.VetBackend.inventario.web.dto.Inventario_Salida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    @Autowired
    private IInventarioService inventarioService;

    @GetMapping
    public ResponseEntity<Page<Inventario_Salida>> mostrarTodosPaginados(Pageable pageable){
        Page<Inventario_Salida> inventarios = inventarioService.obtenerTodosPaginados(pageable);
        if(inventarios.hasContent()){
            return ResponseEntity.ok(inventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Inventario_Salida>> mostrarTodos(){
        List<Inventario_Salida> inventarios = inventarioService.obtenerTodos();
        if(!inventarios.isEmpty()){
            return ResponseEntity.ok(inventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario_Salida> mostrarPorId(@PathVariable Integer id){
        Inventario_Salida inventario = inventarioService.obtenerPorId(id);
        if(inventario != null){
            return ResponseEntity.ok(inventario);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/almacen/{id}")
    public ResponseEntity<List<Inventario_Salida>> mostrarPorAlmacen(@PathVariable Integer id){
        List<Inventario_Salida> inventarios = inventarioService.obtenerPorAlmacenId(id);
        if(!inventarios.isEmpty()){
            return ResponseEntity.ok(inventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Medicamento/{id}")
    public ResponseEntity<List<Inventario_Salida>> mostrarPorMedicamento(@PathVariable Integer id){
        List<Inventario_Salida> inventarios = inventarioService.obtenerPorMedicamentoId(id);
        if(!inventarios.isEmpty()){
            return ResponseEntity.ok(inventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Inventario_Salida> crear(@RequestBody Inventario_Guardar inventarioGuardar){
        Inventario_Salida inventario = inventarioService.crear(inventarioGuardar);
        if(inventario != null){
            return ResponseEntity.ok(inventario);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario_Salida> editar(@PathVariable Integer id, @RequestBody Inventario_Modificar inventarioModificar){
        Inventario_Salida inventario = inventarioService.editar(inventarioModificar);
        if(inventario != null){
            return ResponseEntity.ok(inventario);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Integer id){
        inventarioService.eliminarPorId(id);
        return ResponseEntity.ok("Inventario eliminado correctamente");
    }
}
