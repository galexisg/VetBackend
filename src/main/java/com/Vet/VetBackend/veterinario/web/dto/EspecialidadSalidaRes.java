package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadSalidaRes {
    private Integer  especialidadId;
    private String nombre;
}
