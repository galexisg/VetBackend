package com.Vet.VetBackend.tratamientos.web.controller;


import com.Vet.VetBackend.tratamientos.app.services.TratamientoAplicadoService;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoReq;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas/{citaId}/tratamientos-aplicados")
public class TratamientoAplicadoController {

    private final TratamientoAplicadoService service;
    public TratamientoAplicadoController(TratamientoAplicadoService service) { this.service = service; }

    @GetMapping public List<TratamientoAplicadoRes> listarPorCita(@PathVariable Long citaId) { return service.listarPorCita(citaId); }

    // Vista auxiliar por historial (si te conviene fuera del path de cita, muévelo a otro controller)
    @GetMapping("/historial/{historialId}")
    public List<TratamientoAplicadoRes> listarPorHistorial(@PathVariable Long historialId) {
        return service.listarPorHistorial(historialId);
    }

    @PostMapping
    public TratamientoAplicadoRes registrar(@PathVariable Long citaId, @RequestBody TratamientoAplicadoReq req) {
        req.setCitaId(citaId);
        return service.registrar(req);
    }

    @PutMapping("/{id}/estado")
    public TratamientoAplicadoRes actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return service.actualizarEstado(id, estado);
    }

    @PatchMapping("/{id}/observaciones")
    public TratamientoAplicadoRes agregarObservaciones(@PathVariable Long id, @RequestParam String observaciones) {
        return service.agregarObservaciones(id, observaciones);
    }
}

