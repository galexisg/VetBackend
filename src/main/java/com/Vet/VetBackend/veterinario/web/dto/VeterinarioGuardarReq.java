package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinarioGuardarReq {
    private String numeroLicencia;
    private Set<Integer> especialidadIds;
    private Integer usuarioId;
    private Set<Long> servicioIds; // ← nuevo campo para servicios
}
