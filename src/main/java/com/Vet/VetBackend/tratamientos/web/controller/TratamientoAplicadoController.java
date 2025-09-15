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

    @GetMapping
    public List<TratamientoAplicadoRes> listar(
            @RequestParam(required = false) Long citaId,
            @RequestParam(required = false) Long historialId
    ) {
        if (citaId != null) return service.listarPorCita(citaId);
        if (historialId != null) return service.listarPorHistorial(historialId);
        throw new IllegalArgumentException("Debes enviar citaId o historialId como query param");
    }

    /** POST /api/tratamientos-aplicados */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TratamientoAplicadoRes crear(@RequestBody TratamientoAplicadoReq req) {
        // yo requiero citaId en el body porque ya no viene en el path
        if (req.getCitaId() == null) {
            throw new IllegalArgumentException("citaId es requerido en el body");
        }
        return service.registrar(req);
    }

    /** PUT /api/tratamientos-aplicados/{id}  (yo permito actualizar estado y/o observaciones en un solo golpe) */
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

    /** DTO interno para PUT */
    @Data
    public static class ActualizarReq {
        private String estado;         // p.ej. APLICADO | PENDIENTE | CANCELADO
        private String observaciones;  // texto libre
    }
}
