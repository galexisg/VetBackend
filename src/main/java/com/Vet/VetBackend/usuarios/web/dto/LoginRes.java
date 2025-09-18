package com.Vet.VetBackend.usuarios.web.dto;

public class LoginRes {
    private String token;
    private String nickName;
    private String rol;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
