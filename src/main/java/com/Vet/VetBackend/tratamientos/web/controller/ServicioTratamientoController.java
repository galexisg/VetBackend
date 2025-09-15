package com.Vet.VetBackend.tratamientos.web.controller;


import com.Vet.VetBackend.tratamientos.app.services.ServicioTratamientoService;
import com.Vet.VetBackend.tratamientos.web.dto.ServicioTratamientoReq;
import com.Vet.VetBackend.tratamientos.web.dto.ServicioTratamientoRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios/{servicioId}/tratamientos")
public class ServicioTratamientoController {

    private final ServicioTratamientoService service;
    public ServicioTratamientoController(ServicioTratamientoService service) { this.service = service; }

    @GetMapping public List<ServicioTratamientoRes> listar(@PathVariable Long servicioId) { return service.listarPorServicio(servicioId); }

    @PostMapping
    public ServicioTratamientoRes asociar(@PathVariable Long servicioId, @RequestBody ServicioTratamientoReq req) {
        req.setServicioId(servicioId);
        return service.asociar(req);
    }

    @DeleteMapping("/{id}") // id de la relación servicio_tratamiento
    public void eliminar(@PathVariable Long id) { service.eliminarAsociacion(id); }
}
