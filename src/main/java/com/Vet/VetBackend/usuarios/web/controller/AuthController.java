package com.Vet.VetBackend.usuarios.web.controller;

import com.Vet.VetBackend.usuarios.domain.Usuario;
import com.Vet.VetBackend.usuarios.security.JwtUtils;
import com.Vet.VetBackend.usuarios.app.services.UsuarioService;
import com.Vet.VetBackend.usuarios.web.dto.LoginReq;
import com.Vet.VetBackend.usuarios.web.dto.LoginRes;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtils jwtUtils;

    public AuthController(UsuarioService usuarioService, JwtUtils jwtUtils) {
        this.usuarioService = usuarioService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq req) {
        String nickName = req.getNickName();
        String clave = req.getClave();

        Usuario usuario = usuarioService.obtenerPorNickName(nickName);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Verificar contraseña SHA-256
        if (!usuario.getClave().equals(hashSHA256(clave))) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar token JWT con rol y duración 24h
        String token = jwtUtils.generarToken(usuario.getNickName(), usuario.getRol().getNombre());

        LoginRes res = new LoginRes();
        res.setToken(token);
        res.setNickName(usuario.getNickName());
        res.setRol(usuario.getRol().getNombre());
        return res;
    }

    // Método para hashear la contraseña con SHA-256
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
}
