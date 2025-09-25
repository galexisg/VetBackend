package com.Vet.VetBackend.tratamientos.web.controller;


import com.Vet.VetBackend.tratamientos.app.services.TratamientoService;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoReq;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoRes;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
public class TratamientoController {

    private final TratamientoService service;
    public TratamientoController(TratamientoService service) { this.service = service; }

    @GetMapping
    public List<TratamientoRes> listar() { return service.listar(); }
    @GetMapping("/{id}")
    public TratamientoRes obtener(@PathVariable Long id) { return service.obtenerPorId(id); }

    @PostMapping public TratamientoRes crear(@RequestBody TratamientoReq req) { return service.crear(req); }
    @PutMapping("/{id}") public TratamientoRes actualizar(@PathVariable Long id, @RequestBody TratamientoReq req) { return service.actualizar(id, req); }
    @PatchMapping("/{id}/activar") public void activarInactivar(@PathVariable Long id, @RequestParam boolean activo) { service.activarInactivar(id, activo); }
}