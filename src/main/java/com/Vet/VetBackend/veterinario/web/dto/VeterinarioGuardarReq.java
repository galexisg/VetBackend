package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinarioGuardarReq {
    private String numeroLicencia;
    private Integer especialidadId; // único
    private Integer usuarioId;
    private Long servicioId;        // único
}
