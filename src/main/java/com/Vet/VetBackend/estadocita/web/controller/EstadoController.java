package com.Vet.VetBackend.estadocita.web.controller;

import com.Vet.VetBackend.estadocita.web.dto.EstadoGuardar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoModificar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoSalida;
import com.Vet.VetBackend.estadocita.app.services.IEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;

    @GetMapping
    public ResponseEntity<Page<EstadoSalida>> mostrarTodosPaginados(Pageable pageable) {
        Page<EstadoSalida> estados = estadoService.obtenerTodosPaginados(pageable);
        if (estados.hasContent()) {
            return ResponseEntity.ok(estados);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<EstadoSalida>> mostrarTodos(){
        List<EstadoSalida> estado = estadoService.obtenerTodos();
        if (!estado.isEmpty()){
            return ResponseEntity.ok(estado);
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoSalida> buscarPorId(@PathVariable Integer id){
        EstadoSalida estado = estadoService.obtenerPorId(id);
        if(estado != null){
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EstadoSalida> crear(@RequestBody EstadoGuardar estadoGuardar){
        EstadoSalida estado = estadoService.crear(estadoGuardar);
        return ResponseEntity.ok(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoSalida> editar(@PathVariable Integer id, @RequestBody EstadoModificar estadoModificar){
        EstadoSalida estado = estadoService.editar(estadoModificar);
        return ResponseEntity.ok(estado);
    }




}
