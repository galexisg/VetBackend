package com.Vet.VetBackend.usuarios.web.controller;

import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.domain.Rol;
import com.Vet.VetBackend.usuarios.domain.Estado;
import com.Vet.VetBackend.usuarios.app.services.UsuarioService;
import com.Vet.VetBackend.usuarios.web.dto.UsuarioReq;
import com.Vet.VetBackend.usuarios.web.dto.UsuarioRes;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private UsuarioRes mapToRes(Usuario u) {
        UsuarioRes res = new UsuarioRes();
        res.setId(u.getId());
        res.setNickName(u.getNickName());
        res.setCorreo(u.getCorreo());
        res.setNombreCompleto(u.getNombreCompleto());
        res.setDui(u.getDui());
        res.setTelefono(u.getTelefono());
        res.setDireccion(u.getDireccion());
        res.setFechaNacimiento(u.getFechaNacimiento());
        res.setRol(u.getRol() != null ? u.getRol().getNombre() : null);
        res.setEstado(u.getEstado() != null ? u.getEstado().getNombre() : null);
        return res;
    }

    @GetMapping("/{id}")
    public UsuarioRes obtener(@PathVariable Integer id) {
        Usuario u = usuarioService.obtenerPorId(id);
        if (u == null) throw new RuntimeException("Usuario no encontrado");
        return mapToRes(u);
    }

    @GetMapping("/listarclientes")
    public List<UsuarioRes> listar() {
        return usuarioService.listar()
                .stream()
                .filter(u -> u.getRol() != null && u.getRol().getId() == 2) // ✅ solo usuarios con rol id = 2
                .map(this::mapToRes)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UsuarioRes crear(@RequestBody UsuarioReq req) {
        Usuario usuario = new Usuario();
        usuario.setNickName(req.getNickName());
        usuario.setCorreo(req.getCorreo());
        usuario.setClave(hashSHA256(req.getClave())); // <-- Hash de la contraseña
        usuario.setNombreCompleto(req.getNombreCompleto());
        usuario.setDui(req.getDui());
        usuario.setTelefono(req.getTelefono());
        usuario.setDireccion(req.getDireccion());
        usuario.setFechaNacimiento(req.getFechaNacimiento());

        Rol rol = new Rol();
        rol.setId(req.getRolId());
        usuario.setRol(rol);

        Estado estado = new Estado();
        estado.setId((byte) req.getEstadoId());
        usuario.setEstado(estado);

        return mapToRes(usuarioService.crear(usuario));
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }


    @PutMapping("/{id}")
    public UsuarioRes editar(@PathVariable Integer id, @RequestBody UsuarioReq req) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNickName(req.getNickName());
        usuario.setCorreo(req.getCorreo());

        // Verificar si la clave ya está hasheada
        if (!esClaveHasheada(req.getClave())) {
            usuario.setClave(hashSHA256(req.getClave()));
        } else {
            usuario.setClave(req.getClave());
        }

        usuario.setNombreCompleto(req.getNombreCompleto());
        usuario.setDui(req.getDui());
        usuario.setTelefono(req.getTelefono());
        usuario.setDireccion(req.getDireccion());
        usuario.setFechaNacimiento(req.getFechaNacimiento());

        Rol rol = new Rol();
        rol.setId(req.getRolId());
        usuario.setRol(rol);

        Estado estado = new Estado();
        estado.setId((byte) req.getEstadoId());
        usuario.setEstado(estado);

        return mapToRes(usuarioService.editar(usuario));
    }

    // Metodo auxiliar para verificar SHA-256
    private boolean esClaveHasheada(String clave) {
        return clave != null && clave.matches("^[a-fA-F0-9]{64}$");
    }

    @PatchMapping("/activar/{id}")
    public void activar(@PathVariable Integer id) {
        usuarioService.activar(id);
    }

    @PatchMapping("/desactivar/{id}")
    public void desactivar(@PathVariable Integer id) {
        usuarioService.desactivar(id);
    }
}
