package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDiaSalidaRes {
    private Integer estadoDiaId;
    private String estado; // ⚠️ solo String
}
