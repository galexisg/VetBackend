package com.Vet.VetBackend.usuarios.web.dto;

import java.time.LocalDateTime;

public class LoginRes {
    private String token;
    private LocalDateTime expiresAt;
    private UserDto user;

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    // Sub-DTO para los datos del usuario
    public static class UserDto {
        private Integer id;
        private String nickName;
        private String nombreCompleto;
        private String rol;
        private String estado;

        // Getters y Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getNickName() { return nickName; }
        public void setNickName(String nickName) { this.nickName = nickName; }
        public String getNombreCompleto() { return nombreCompleto; }
        public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}
