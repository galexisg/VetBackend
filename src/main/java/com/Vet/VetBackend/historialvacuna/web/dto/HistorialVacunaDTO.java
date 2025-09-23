package com.Vet.VetBackend.historialvacuna.web.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialVacunaDTO {

    private Integer historialVacunaId;
    private Integer vacunaId;
    private Integer mascotaId;
    private Integer veterinarioId;
    private LocalDate fecha;
    private Integer medicamentoId;
    private Integer loteId;
    private String observacion;

}