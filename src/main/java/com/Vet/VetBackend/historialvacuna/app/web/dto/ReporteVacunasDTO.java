package com.Vet.VetBackend.historialvacuna.app.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteVacunasDTO {
    private String nombreVacuna;
    private Long cantidadAplicada;
    private Long mascotasAtendidas;
}