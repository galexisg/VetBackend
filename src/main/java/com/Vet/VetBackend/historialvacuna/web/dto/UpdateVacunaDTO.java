package com.Vet.VetBackend.historialvacuna.web.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVacunaDTO {
    private LocalDate fecha;
    private Integer medicamentoId;
    private Integer loteId;
    private String observacion;
}