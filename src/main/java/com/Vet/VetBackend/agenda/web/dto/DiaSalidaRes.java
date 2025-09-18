package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DiaSalidaRes {
    private int diaId;
    private String nombre;
    private EstadoDiaSalidaRes estadoDia;
}
