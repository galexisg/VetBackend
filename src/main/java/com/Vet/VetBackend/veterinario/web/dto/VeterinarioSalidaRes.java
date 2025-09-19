package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinarioSalidaRes {
    private int id;
    private String numeroLicencia;
    private String estado;
    private Set<String> especialidades;
    private Set<String> servicios;

}
