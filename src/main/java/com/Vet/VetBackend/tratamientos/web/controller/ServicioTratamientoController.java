package com.Vet.VetBackend.tratamientos.web.controller;

import com.Vet.VetBackend.tratamientos.app.services.ServicioTratamientoService;
import com.Vet.VetBackend.tratamientos.web.dto.ServicioTratamientoReq;
import com.Vet.VetBackend.tratamientos.web.dto.ServicioTratamientoRes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicio-tratamientos")
public class ServicioTratamientoController {

    private final ServicioTratamientoService service;

    public ServicioTratamientoController(ServicioTratamientoService service) {
        this.service = service;
    }

    // GET /api/servicio-tratamientos?servicioId=1
    @GetMapping
    public List<ServicioTratamientoRes> listar(@RequestParam Long servicioId) {
        return service.listarPorServicio(servicioId);
    }

    // POST /api/servicio-tratamientos
    @PostMapping
    public ServicioTratamientoRes asociar(@RequestBody ServicioTratamientoReq req) {
        return service.asociar(req);
    }

    // DELETE /api/servicio-tratamientos/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminarAsociacion(id);
    }
}
