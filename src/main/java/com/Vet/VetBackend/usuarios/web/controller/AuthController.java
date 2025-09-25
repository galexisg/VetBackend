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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

        // Calcular expiración del token (24h)
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        // Construir respuesta
        LoginRes res = new LoginRes();
        res.setToken(token);
        res.setExpiresAt(expiresAt);

        LoginRes.UserDto userDto = new LoginRes.UserDto();
        userDto.setId(usuario.getId());
        userDto.setNickName(usuario.getNickName());
        userDto.setNombreCompleto(usuario.getNombreCompleto());
        userDto.setRol(usuario.getRol().getNombre());
        userDto.setEstado(usuario.getEstado().getNombre());

        res.setUser(userDto);

        return res;
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
}
