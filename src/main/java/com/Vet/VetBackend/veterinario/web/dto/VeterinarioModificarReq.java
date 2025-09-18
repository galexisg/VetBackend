package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinarioModificarReq {
    private int id;
    private String numeroLicencia;
    private String estado; // "Activo" o "Inactivo"
}
