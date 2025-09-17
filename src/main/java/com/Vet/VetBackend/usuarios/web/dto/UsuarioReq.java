package com.Vet.VetBackend.usuarios.web.dto;

import java.time.LocalDate;

public class UsuarioReq {

    private String nickName;
    private String correo;
    private String clave;
    private String nombreCompleto;
    private String dui;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    private Byte rolId;
    private Byte estadoId;

    // Getters y Setters
    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public Byte getRolId() { return rolId; }
    public void setRolId(Byte rolId) { this.rolId = rolId; }
    public Byte getEstadoId() { return estadoId; }
    public void setEstadoId(Byte estadoId) { this.estadoId = estadoId; }
}
