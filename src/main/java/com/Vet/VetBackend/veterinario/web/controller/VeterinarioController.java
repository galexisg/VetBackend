package com.Vet.VetBackend.veterinario.web.controller;

import com.Vet.VetBackend.usuarios.repo.UsuarioRepository;
import com.Vet.VetBackend.veterinario.app.services.IVeterinarioService;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioGuardarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioModificarReq;
import com.Vet.VetBackend.veterinario.web.dto.VeterinarioSalidaRes;
import com.Vet.VetBackend.veterinario.web.dto.UsuarioSalidaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final IVeterinarioService servicio;
    private final UsuarioRepository usuarioRepo;

    // Guardar nuevo veterinario
    @PostMapping
    public VeterinarioSalidaRes guardar(@RequestBody VeterinarioGuardarReq dto) {
        return servicio.guardar(dto);
    }

    // Listar todos (activos e inactivos)
    @GetMapping
    public List<VeterinarioSalidaRes> listar() {
        return servicio.listar();
    }

    // Listar por ID
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioSalidaRes> buscarPorId(@PathVariable Integer id) {
        VeterinarioSalidaRes veterinario = servicio.buscarPorId(id);
        return ResponseEntity.ok(veterinario);
    }

    // Listar solo activos
    @GetMapping("/activos")
    public List<VeterinarioSalidaRes> listarActivos() {
        return servicio.listarActivos();
    }

    // Listar solo inactivos
    @GetMapping("/inactivos")
    public List<VeterinarioSalidaRes> listarInactivos() {
        return servicio.listarInactivos();
    }

    // Modificar veterinario
    @PutMapping("/{id}")
    public VeterinarioSalidaRes modificar(@PathVariable int id, @RequestBody VeterinarioModificarReq dto) {
        dto.setId(id);
        return servicio.modificar(dto);
    }

    // Inactivar veterinario (soft delete)
    @PutMapping("/{id}/inactivar")
    public ResponseEntity<String> inactivar(@PathVariable int id) {
        servicio.inactivar(id);
        return ResponseEntity.ok("Veterinario marcado como Inactivo con éxito");
    }

    // Activar veterinario
    @PutMapping("/{id}/activar")
    public ResponseEntity<String> activar(@PathVariable int id) {
        servicio.activar(id);
        return ResponseEntity.ok("Veterinario activado con éxito");
    }

    // Listar solo usuarios con rol Veterinario
    @GetMapping("/usuarios")
    public List<UsuarioSalidaRes> listarUsuariosVeterinarios() {
        return usuarioRepo.findAll().stream()
                .filter(u -> u.getRol() != null && u.getRol().getNombre().equalsIgnoreCase("Veterinario"))
                .map(u -> UsuarioSalidaRes.builder()
                        .id(u.getId())
                        .nickName(u.getNickName())
                        .correo(u.getCorreo())
                        .nombreCompleto(u.getNombreCompleto())
                        .dui(u.getDui())
                        .telefono(u.getTelefono())
                        .direccion(u.getDireccion())
                        .fechaNacimiento(u.getFechaNacimiento())
                        .rol(u.getRol().getNombre())
                        .estado(u.getEstado().getNombre().equalsIgnoreCase("activo") ? "Activo" : "Inactivo")
                        .build())
                .toList();
    }
}
