package com.Vet.VetBackend.mascotas.web.controller;

import com.Vet.VetBackend.mascotas.app.services.MascotaService;
import com.Vet.VetBackend.mascotas.domain.Mascota;
import com.Vet.VetBackend.mascotas.web.dto.MascotaReq;
import com.Vet.VetBackend.mascotas.web.dto.MascotaRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping
    public ResponseEntity<MascotaRes> crear(@RequestBody MascotaReq request) {
        Mascota mascota = mapToEntity(request);
        Mascota guardada = mascotaService.crear(mascota);
        return ResponseEntity.ok(mapToResponse(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaRes> actualizar(@PathVariable Integer id, @RequestBody MascotaReq request) {
        Mascota mascota = mapToEntity(request);
        Mascota actualizada = mascotaService.actualizar(id, mascota);
        return ResponseEntity.ok(mapToResponse(actualizada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaRes> obtenerPorId(@PathVariable Integer id) {
        Mascota mascota = mascotaService.obtenerPorId(id);
        return ResponseEntity.ok(mapToResponse(mascota));
    }

    @GetMapping
    public ResponseEntity<List<MascotaRes>> listarTodos() {
        List<MascotaRes> lista = mascotaService.listarTodos()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // Métodos de mapeo
    private Mascota mapToEntity(MascotaReq request) {
        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());
        mascota.setFoto(request.getFoto());
        mascota.setAlergia(request.getAlergia());
        mascota.setNacimiento(request.getNacimiento());
        mascota.setSexo(request.getSexo());
        mascota.setPeso(request.getPeso());
        // Aquí deberías setear usuario y raza usando sus repositorios
        return mascota;
    }

    private MascotaRes mapToResponse(Mascota mascota) {
        MascotaRes response = new MascotaRes();
        response.setId(mascota.getId());
        response.setNombre(mascota.getNombre());
        response.setFoto(mascota.getFoto());
        response.setAlergia(mascota.getAlergia());
        response.setNacimiento(mascota.getNacimiento());
        response.setSexo(mascota.getSexo());
        response.setPeso(mascota.getPeso());
        // if (mascota.getUsuario() != null) response.setUsuarioId(mascota.getUsuario().getId());
        // if (mascota.getRaza() != null) response.setRazaId(mascota.getRaza().getId());
        return response;
    }
}
