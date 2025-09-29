package com.Vet.VetBackend.tratamientos.web.controller;

import com.Vet.VetBackend.tratamientos.app.services.TratamientoAplicadoService;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoReq;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoRes;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tratamientos-aplicados", produces = "application/json")
public class TratamientoAplicadoController {

    private final TratamientoAplicadoService service;

    public TratamientoAplicadoController(TratamientoAplicadoService service) {
        this.service = service;
    }

    /**
     * GET /api/tratamientos-aplicados?citaId=1
     * Lista los tratamientos aplicados a una cita específica
     */
    @GetMapping
    public List<TratamientoAplicadoRes> listar(@RequestParam(required = true) Long citaId) {
        return service.listarPorCita(citaId);
    }

    /**
     * POST /api/tratamientos-aplicados
     * Registra un nuevo tratamiento aplicado
     */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TratamientoAplicadoRes crear(@RequestBody TratamientoAplicadoReq req) {
        if (req.getCitaId() == null) {
            throw new IllegalArgumentException("citaId es requerido en el body");
        }
        return service.registrar(req);
    }

    /**
     * PUT /api/tratamientos-aplicados/{id}
     * Actualiza estado y/o observaciones de un tratamiento aplicado
     */
    @PutMapping(path = "/{id}", consumes = "application/json")
    public TratamientoAplicadoRes actualizar(@PathVariable Long id, @RequestBody ActualizarReq body) {
        TratamientoAplicadoRes res = null;

        if (body.getEstado() != null && !body.getEstado().isBlank()) {
            res = service.actualizarEstado(id, body.getEstado());
        }
        if (body.getObservaciones() != null) {
            res = service.agregarObservaciones(id, body.getObservaciones());
        }

        if (res == null) {
            throw new IllegalArgumentException("Nada que actualizar: envía 'estado' y/o 'observaciones'");
        }
        return res;
    }

    /**
     * DTO interno para actualizar
     */
    @Data
    public static class ActualizarReq {
        private String estado;         // PLANIFICADO | EN_CURSO | APLICADO | CANCELADO
        private String observaciones;  // Texto libre
    }
}