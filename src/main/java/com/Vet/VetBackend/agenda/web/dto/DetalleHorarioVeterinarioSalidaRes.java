package com.Vet.VetBackend.agenda.web.dto;

import lombok.*;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "detalleHorarioVeterinarioId",
        "veterinarioId",
        "nombreVeterinario",
        "diaId",
        "dia",
        "estado",
        "bloqueHorarioId",
        "horaInicio",
        "horaFin"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleHorarioVeterinarioSalidaRes {
    private Integer detalleHorarioVeterinarioId;
    private Integer veterinarioId;
    private String nombreVeterinario;
    private Integer diaId;
    private String Dia;
    private String estado;   // 👈 nuevo campo
    private Integer bloqueHorarioId;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}