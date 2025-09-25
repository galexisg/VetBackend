package com.Vet.VetBackend.servicios.web.controller;

import com.Vet.VetBackend.servicios.app.services.ServicioService;
import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import com.Vet.VetBackend.servicios.web.dto.ServicioEstadoReq;
import com.Vet.VetBackend.servicios.web.dto.MotivoRes;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService svc;

    public ServicioController(ServicioService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<ServicioRes> crear(@Validated @RequestBody ServicioReq req) {
        return new ResponseEntity<>(svc.crear(req), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioRes> actualizar(@PathVariable Long id, @Validated @RequestBody ServicioReq req) {
        return new ResponseEntity<>(svc.actualizar(id, req), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioRes> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(svc.obtener(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ServicioRes>> listar(@RequestParam(required = false) String q,
                                                    @RequestParam(required = false) Boolean activo,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(svc.listar(q, activo, page, size), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServicioRes> cambiarEstado(
            @PathVariable Long id,
            @Validated @RequestBody ServicioEstadoReq req) {
        return new ResponseEntity<>(svc.cambiarEstado(id, req), HttpStatus.OK);
    }

    // ✅ NUEVO: obtener motivos asociados a un servicio
    @GetMapping("/{id}/motivos")
    public ResponseEntity<List<MotivoRes>> listarMotivosPorServicio(@PathVariable Long id) {
        return new ResponseEntity<>(svc.listarMotivosPorServicio(id), HttpStatus.OK);
    }
}
