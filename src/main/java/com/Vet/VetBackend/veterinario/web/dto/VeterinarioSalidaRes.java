package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinarioSalidaRes {
    private int id;
    private String numeroLicencia;
    private String estado;
    private String especialidad; // único
    private String servicio;     // único
    private UsuarioSalidaRes usuario; // 🔹 ahora incluye todos los datos del usuario
}
