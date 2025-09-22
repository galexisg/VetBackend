package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleHorarioVeterinarioSalidaRes {
    private Integer detalleHorarioVeterinarioId;
    private Integer veterinarioId;
    private String nombreVeterinario;
    private Integer diaId;
    private String nombreDia;
    private Integer bloqueHorarioId;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}