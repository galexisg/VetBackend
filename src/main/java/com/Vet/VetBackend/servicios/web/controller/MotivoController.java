// src/main/java/com/Vet/VetBackend/servicios/web/controller/MotivoController.java
package com.Vet.VetBackend.servicios.web.controller;

import com.Vet.VetBackend.servicios.app.services.MotivoService;
import com.Vet.VetBackend.servicios.web.dto.MotivoReq;
import com.Vet.VetBackend.servicios.web.dto.MotivoRes;
import com.Vet.VetBackend.servicios.web.dto.MotivoServicioReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motivos")
public class MotivoController {

    private final MotivoService svc;
    public MotivoController(MotivoService svc) { this.svc = svc; }

    @PostMapping
    public MotivoRes crear(@Validated @RequestBody MotivoReq req) { return svc.crear(req); }

    @PutMapping("/{id}")
    public MotivoRes actualizar(@PathVariable Short id, @Validated @RequestBody MotivoReq req) {
        return svc.actualizar(id, req);
    }

    @GetMapping("/{id}")
    public MotivoRes obtener(@PathVariable Short id) { return svc.obtener(id); }

    @GetMapping
    public List<MotivoRes> listar() { return svc.listar(); }

    @PostMapping("/vincular")
    public void vincular(@Validated @RequestBody MotivoServicioReq req) {
        svc.vincular(req.getMotivoId(), req.getServicioId());
    }

    @DeleteMapping("/desvincular")
    public void desvincular(@Validated @RequestBody MotivoServicioReq req) {
        svc.desvincular(req.getMotivoId(), req.getServicioId());
    }
}
