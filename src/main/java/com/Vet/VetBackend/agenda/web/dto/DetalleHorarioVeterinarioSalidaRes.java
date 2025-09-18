package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleHorarioVeterinarioSalidaRes {
    private Integer detalleHorarioVeterinarioId;
    private int veterinarioId;
    private String nombreVeterinario;
    private Integer diaId;
    private String nombre;
    private Byte bloqueHorarioId;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
