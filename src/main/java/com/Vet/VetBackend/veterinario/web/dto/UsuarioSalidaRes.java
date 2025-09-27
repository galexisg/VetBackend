package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSalidaRes {
    private Integer id;
    private String nickName;
    private String correo;
    private String nombreCompleto;
    private String dui;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String rol;     // nombre del rol
    private String estado;  // nombre del estado
}
