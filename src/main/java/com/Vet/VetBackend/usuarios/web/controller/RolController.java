package com.Vet.VetBackend.usuarios.web.controller;

import com.Vet.VetBackend.usuarios.domain.Rol;
import com.Vet.VetBackend.usuarios.app.services.RolService;
import com.Vet.VetBackend.usuarios.web.dto.RolRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<RolRes> listar() {
        return rolService.listar().stream()
                .map(r -> new RolRes(r.getId(), r.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RolRes obtenerPorId(@PathVariable Byte id) {
        Rol rol = rolService.obtenerPorId(id);
        return new RolRes(rol.getId(), rol.getNombre());
    }
}

