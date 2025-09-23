package com.Vet.VetBackend.raza.web.controller;

import com.Vet.VetBackend.raza.domain.Raza;
import com.Vet.VetBackend.raza.web.dto.RazaGuardar;
import com.Vet.VetBackend.raza.web.dto.RazaModificar;
import com.Vet.VetBackend.raza.web.dto.RazaSalida;
import com.Vet.VetBackend.raza.app.services.IRazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raza")
public class RazaController {

    @Autowired
    private IRazaService razaService;

   @GetMapping
   public ResponseEntity<Page<RazaSalida>> mostrarTodosPaginados(Pageable pageable) {
       Page<RazaSalida> raza = razaService.obtenerTodosPaginados(pageable);
        if (raza.hasContent()) {
         return ResponseEntity.ok(raza);
       }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<RazaSalida>> mostrarTodos(){
        List<RazaSalida> Medicamento = razaService.obtenerTodos();
        if (!Medicamento.isEmpty()){
            return ResponseEntity.ok(Medicamento);
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RazaSalida> buscarPorId(@PathVariable Integer id){
        RazaSalida Medicamento= razaService.obtenerPorId(id);
        if(Medicamento != null){
            return ResponseEntity.ok(Medicamento);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RazaSalida> crear(@RequestBody RazaGuardar razaGuardar){
        RazaSalida Raza = razaService.crear(razaGuardar);
        return ResponseEntity.ok(Raza);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RazaSalida> editar(@PathVariable Integer id, @RequestBody RazaModificar razaModificar){
        RazaSalida Raza = razaService.editar(razaModificar);
        return ResponseEntity.ok(Raza);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Integer id){
        razaService.eliminarPorId(id);
        return ResponseEntity.ok().body("raza eliminada");
    }


}