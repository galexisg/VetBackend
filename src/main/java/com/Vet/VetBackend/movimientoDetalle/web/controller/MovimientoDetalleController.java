package com.Vet.VetBackend.movimientoDetalle.web.controller;

import com.Vet.VetBackend.movimientoDetalle.app.services.IMovimientoDetalleService;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Guardar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Modificar;
import com.Vet.VetBackend.movimientoDetalle.web.dto.MovimientoDetalle_Salida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientoDetalle")
public class MovimientoDetalleController {
    @Autowired
    private IMovimientoDetalleService movimientoDetalleService;

    @GetMapping
    public ResponseEntity<Page<MovimientoDetalle_Salida>> mostrarTodosPaginados(Pageable pageable){
        Page<MovimientoDetalle_Salida> movimientoDetalles = movimientoDetalleService.obtenerTodosPaginados(pageable);
        if(movimientoDetalles.hasContent()){
            return ResponseEntity.ok(movimientoDetalles);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<MovimientoDetalle_Salida>> mostrarTodos(){
        List<MovimientoDetalle_Salida> movimientoDetalles = movimientoDetalleService.obtenerTodos();
        if(!movimientoDetalles.isEmpty()){
            return ResponseEntity.ok(movimientoDetalles);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDetalle_Salida> mostrarPorId(@PathVariable Integer id){
        MovimientoDetalle_Salida movimientoDetalle = movimientoDetalleService.obtenerPorId(id);
        if(movimientoDetalle != null){
            return ResponseEntity.ok(movimientoDetalle);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movimientoInventario/{id}")
    public ResponseEntity<List<MovimientoDetalle_Salida>> mostrarPorMovimientoInventario(@PathVariable Integer id){
        List<MovimientoDetalle_Salida> movimientoDetalle = movimientoDetalleService.obtenerPorMovimientoInventarioId(id);
        if(!movimientoDetalle.isEmpty()){
            return ResponseEntity.ok(movimientoDetalle);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Medicamento/{id}")
    public ResponseEntity<List<MovimientoDetalle_Salida>> mostrarPorMedicamento(@PathVariable Integer id){
        List<MovimientoDetalle_Salida> movimientoDetalle = movimientoDetalleService.obtenerPorMedicamentoId(id);
        if(!movimientoDetalle.isEmpty()){
            return ResponseEntity.ok(movimientoDetalle);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/loteMedicamento/{id}")
    public ResponseEntity<List<MovimientoDetalle_Salida>> mostrarPorLoteMedicamento(@PathVariable Integer id){
        List<MovimientoDetalle_Salida> movimientoDetalle = movimientoDetalleService.obtenerPorLoteMedicamento(id);
        if(!movimientoDetalle.isEmpty()){
            return ResponseEntity.ok(movimientoDetalle);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MovimientoDetalle_Salida> crear(@RequestBody MovimientoDetalle_Guardar movimientoDetalleGuardar){
        MovimientoDetalle_Salida movimientoDetalle = movimientoDetalleService.crear(movimientoDetalleGuardar);
        if(movimientoDetalle != null){
            return ResponseEntity.ok(movimientoDetalle);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDetalle_Salida> editar(@PathVariable Integer id, @RequestBody MovimientoDetalle_Modificar movimientoDetalleModificar){
        MovimientoDetalle_Salida movimientoDetalle = movimientoDetalleService.editar(movimientoDetalleModificar);
        if(movimientoDetalle != null){
            return ResponseEntity.ok(movimientoDetalle);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Integer id){
        movimientoDetalleService.eliminarPorId(id);
        return ResponseEntity.ok("Inventario eliminado correctamente");
    }
}
