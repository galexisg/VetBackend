package com.Vet.VetBackend.vacuna.web.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class VacunaResponseDTO {
    private Integer vacunaId;
    private String nombre;
    private Boolean estado;
}
