// src/main/java/com/Vet/VetBackend/servicios/web/controller/ServicioController.java
package com.Vet.VetBackend.servicios.web.controller;

import com.Vet.VetBackend.servicios.app.services.ServicioService;
import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService svc;
    public ServicioController(ServicioService svc) { this.svc = svc; }

    @PostMapping
    public ServicioRes crear(@Validated @RequestBody ServicioReq req) { return svc.crear(req); }

    @PutMapping("/{id}")
    public ServicioRes actualizar(@PathVariable Long id, @Validated @RequestBody ServicioReq req) {
        return svc.actualizar(id, req);
    }

    @PatchMapping("/{id}/estado")
    public ServicioRes estado(@PathVariable Long id, @RequestParam boolean activo) {
        return svc.activar(id, activo);
    }

    @GetMapping("/{id}")
    public ServicioRes obtener(@PathVariable Long id) { return svc.obtener(id); }

    @GetMapping
    public Page<ServicioRes> listar(@RequestParam(required = false) String q,
                                    @RequestParam(required = false) Boolean activo,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return svc.listar(q, activo, page, size);
    }
}
