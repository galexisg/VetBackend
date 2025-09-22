package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaSalidaRes {
    private Integer diaId;
    private String nombre;
    private String estado; // Solo el estado como string
}
