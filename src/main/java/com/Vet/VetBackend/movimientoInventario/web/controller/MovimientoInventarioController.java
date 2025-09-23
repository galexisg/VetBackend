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
    public ResponseEntity<Page<MovimientoInventario_Salida>> mostrarTodosPaginados(Pageable pageable) {
        Page<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerTodosPaginados(pageable);
        return movimientoInventarios.hasContent()
                ? ResponseEntity.ok(movimientoInventarios)
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<MovimientoInventario_Salida>> mostrarTodos() {
        List<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerTodos();
        return movimientoInventarios.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(movimientoInventarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario_Salida> mostrarPorId(@PathVariable Integer id) {
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.obtenerPorId(id);
        return movimientoInventario != null
                ? ResponseEntity.ok(movimientoInventario)
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/almacen/{id}")
    public ResponseEntity<List<MovimientoInventario_Salida>> mostrarPorAlmacen(@PathVariable Integer id) {
        List<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerPorAlmacenId(id);
        return movimientoInventarios.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(movimientoInventarios);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<MovimientoInventario_Salida>> mostrarPorUsuario(@PathVariable Integer id) {
        List<MovimientoInventario_Salida> movimientoInventarios = movimientoInventarioService.obtenerPorUsuarioId(id);
        return movimientoInventarios.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(movimientoInventarios);
    }

    @PostMapping
    public ResponseEntity<MovimientoInventario_Salida> crear(@RequestBody MovimientoInventario_Guardar dto) {
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.crear(dto);
        return movimientoInventario != null
                ? ResponseEntity.ok(movimientoInventario)
                : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoInventario_Salida> editar(@PathVariable Integer id, @RequestBody MovimientoInventario_Modificar dto) {
        dto.setId(id); // Asegura que el ID se propague correctamente
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.editar(dto);
        return movimientoInventario != null
                ? ResponseEntity.ok(movimientoInventario)
                : ResponseEntity.internalServerError().build();
    }

    @PatchMapping
    public ResponseEntity<MovimientoInventario_Salida> cambiarTipo(@RequestBody MovimientoInventarioCambiarTipo dto) {
        MovimientoInventario_Salida movimientoInventario = movimientoInventarioService.cambiarTipo(dto);
        return movimientoInventario != null
                ? ResponseEntity.ok(movimientoInventario)
                : ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        movimientoInventarioService.eliminarPorId(id);
        return ResponseEntity.ok("Movimiento de inventario eliminado correctamente");
    }
}
