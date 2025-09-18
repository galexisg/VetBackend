package com.Vet.VetBackend.movimientoInventario.web.controller;

import com.Vet.VetBackend.movimientoInventario.app.services.IMovimientoInventarioService;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventarioCambiarTipo;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Guardar;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Modificar;
import com.Vet.VetBackend.movimientoInventario.web.dto.MovimientoInventario_Salida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientoInventario")
public class MovimientoInventarioController {
    @Autowired
    private IMovimientoInventarioService movimientoInventarioService;

    @GetMapping
    public ResponseEntity<Page<MovimientoInventario_Salida>> mostrarTodosPaginados(Pageable pageable){
        Page<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerTodosPaginados(pageable);
        if(movimientoInventarios.hasContent()){
            return ResponseEntity.ok(movimientoInventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<MovimientoInventario_Salida>> mostrarTodos(){
        List<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerTodos();
        if(!movimientoInventarios.isEmpty()){
            return ResponseEntity.ok(movimientoInventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario_Salida> mostrarPorId(@PathVariable Integer id){
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.obtenerPorId(id);
        if(movimientoInventario != null){
            return ResponseEntity.ok(movimientoInventario);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/almacen/{id}")
    public ResponseEntity<List<MovimientoInventario_Salida>> mostrarPorAlmacen(@PathVariable Integer id){
        List<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerPorAlmacenId(id);
        if(!movimientoInventarios.isEmpty()){
            return ResponseEntity.ok(movimientoInventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<MovimientoInventario_Salida>> mostrarPorUsuario(@PathVariable Integer id){
        List<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerPorUsuarioId(id);
        if(!movimientoInventarios.isEmpty()){
            return ResponseEntity.ok(movimientoInventarios);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MovimientoInventario_Salida> crear(@RequestBody MovimientoInventario_Guardar movimientoInventarioGuardar){
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.crear(movimientoInventarioGuardar);
        if(movimientoInventario != null){
            return ResponseEntity.ok(movimientoInventario);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoInventario_Salida> editar(@PathVariable Long id, @RequestBody MovimientoInventario_Modificar movimientoInventarioModificar){
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.editar(movimientoInventarioModificar);
        if(movimientoInventario != null){
            return ResponseEntity.ok(movimientoInventario);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PatchMapping
    public ResponseEntity<MovimientoInventario_Salida> cambiarTipo(@RequestBody MovimientoInventarioCambiarTipo movimientoInventarioCambiarTipo){
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.cambiarTipo(movimientoInventarioCambiarTipo);

        if(movimientoInventario != null){
            return ResponseEntity.ok(movimientoInventario);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Integer id){
        movimientoInventarioService.eliminarPorId(id);
        return ResponseEntity.ok("Movimiento de inventario eliminado correctamente");
    }
}
