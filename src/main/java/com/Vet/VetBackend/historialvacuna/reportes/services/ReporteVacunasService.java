package com.Vet.VetBackend.historialvacuna.reportes.services;

import com.Vet.VetBackend.reportes.dto.ReporteVacunasDTO;
import java.time.LocalDate;
import java.util.List;

public interface ReporteVacunasService {
    List<ReporteVacunasDTO> generarReporte(LocalDate inicio, LocalDate fin);

    interface ReporteVacunasService {
        List<ReporteVacunasDTO> generarReporte(LocalDate inicio, LocalDate fin);
    }
}