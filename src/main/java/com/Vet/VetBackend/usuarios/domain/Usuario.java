package com.Vet.VetBackend.usuarios.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuarioId")
    private Integer id;

    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RolId", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EstadoId", nullable = false)
    private Estado estado;

    @Column(name = "NickName", length = 60, nullable = false)
    private String nickName;

    @Column(name = "Correo", length = 120, nullable = false)
    private String correo;

    @Column(name = "Clave", length = 255, nullable = false)
    private String clave;

    @Column(name = "NombreCompleto", length = 120, nullable = false)
    private String nombreCompleto;

    @Column(name = "Dui", length = 20)
    private String dui;

    @Column(name = "Telefono", length = 32)
    private String telefono;

    @Column(name = "Direccion", length = 200)
    private String direccion;

    @Column(name = "FechaNacimiento")
    private LocalDate fechaNacimiento;

    // --- ctors ---
    public Usuario() {}

    public Usuario(Rol rol, Estado estado, String nickName, String correo, String clave, String nombreCompleto) {
        this.rol = rol;
        this.estado = estado;
        this.nickName = nickName;
        this.correo = correo;
        this.clave = clave;
        this.nombreCompleto = nombreCompleto;
    }

    // --- getters/setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

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
}
