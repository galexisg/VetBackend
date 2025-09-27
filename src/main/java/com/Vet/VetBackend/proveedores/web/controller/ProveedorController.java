package com.Vet.VetBackend.proveedores.web.controller;

import com.Vet.VetBackend.proveedores.web.dto.ProveedorGuardar;
import com.Vet.VetBackend.proveedores.web.dto.ProveedorModificar;
import com.Vet.VetBackend.proveedores.web.dto.ProveedorSalida;
import com.Vet.VetBackend.proveedores.app.services.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<Page<ProveedorSalida>> mostrarTodosPaginados(Pageable pageable) {
        Page<ProveedorSalida> proveedores = proveedorService.obtenerTodosPaginados(pageable);
        if (proveedores.hasContent()) {
            return ResponseEntity.ok(proveedores);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ProveedorSalida>> mostrarTodos(){
        List<ProveedorSalida> Proveedor = proveedorService.obtenerTodos();
        if (!Proveedor.isEmpty()){
            return ResponseEntity.ok(Proveedor);
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorSalida> buscarPorId(@PathVariable Integer id){
        ProveedorSalida Proveedor = proveedorService.obtenerPorId(id);
        if(Proveedor != null){
            return ResponseEntity.ok(Proveedor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProveedorSalida> crear(@RequestBody ProveedorGuardar proveedorGuardar){
        ProveedorSalida Proveedor = proveedorService.crear(proveedorGuardar);
        return ResponseEntity.ok(Proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorSalida> editar(@PathVariable Integer id, @RequestBody ProveedorModificar proveedorModificar){
        ProveedorSalida Proveedor = proveedorService.editar(proveedorModificar);
        return ResponseEntity.ok(Proveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Integer id){
        proveedorService.eliminarPorId(id);
        return ResponseEntity.ok().body("Proveedor eliminada correctamente");
    }


}

