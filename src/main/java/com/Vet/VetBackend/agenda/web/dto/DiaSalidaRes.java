package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaSalidaRes {

    private Integer diaId; // ⚠️ Se cambió a Integer para consistencia
    private String nombre;
    private EstadoDiaSalidaRes estadoDia;
}