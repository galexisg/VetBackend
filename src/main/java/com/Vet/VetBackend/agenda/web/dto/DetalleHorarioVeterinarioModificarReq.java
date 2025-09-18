package com.Vet.VetBackend.agenda.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleHorarioVeterinarioModificarReq {
    private int veterinarioId;
    private int diaId;
    private Byte bloqueHorarioId;
}
