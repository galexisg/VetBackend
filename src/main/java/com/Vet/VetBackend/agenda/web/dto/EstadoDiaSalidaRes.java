package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EstadoDiaSalidaRes {
    private int estadoDiaId;
    private String nombre;
}
