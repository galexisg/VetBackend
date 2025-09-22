package com.Vet.VetBackend.vacuna.web.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class VacunaEstadoDTO {
    private Integer vacunaId;
    private Boolean estado;
}
