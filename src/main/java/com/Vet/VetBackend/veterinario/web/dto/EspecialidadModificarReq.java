package com.Vet.VetBackend.veterinario.web.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadModificarReq {
    private Integer  especialidadId;
    private String nombre;
    private Boolean activo;
}
