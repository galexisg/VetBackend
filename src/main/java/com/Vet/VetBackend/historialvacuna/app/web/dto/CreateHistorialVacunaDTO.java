package com.Vet.VetBackend.historialvacuna.app.web.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHistorialVacunaDTO {

    private Integer vacunaId;
    private Integer mascotaId;
    private Integer veterinarioId;
    private LocalDate fecha;
    private Integer medicamentoId;
    private Integer loteId;
    private String observacion;

}