package com.Vet.VetBackend.usuarios.web.dto;

public class LoginReq {
    private String nickName;
    private String clave;

    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
