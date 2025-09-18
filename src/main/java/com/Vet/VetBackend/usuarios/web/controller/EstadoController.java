package com.Vet.VetBackend.usuarios.web.controller;

import com.Vet.VetBackend.usuarios.domain.Estado;
import com.Vet.VetBackend.usuarios.app.services.EstadoService;
import com.Vet.VetBackend.usuarios.web.dto.EstadoRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public List<EstadoRes> listar() {
        return estadoService.listar().stream()
                .map(e -> new EstadoRes(e.getId(), e.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EstadoRes obtenerPorId(@PathVariable Byte id) {
        Estado estado = estadoService.obtenerPorId(id);
        return new EstadoRes(estado.getId(), estado.getNombre());
    }
}

