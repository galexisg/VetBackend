package com.Vet.VetBackend.historialvacuna.reportes.dto; // Make sure this is correct

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