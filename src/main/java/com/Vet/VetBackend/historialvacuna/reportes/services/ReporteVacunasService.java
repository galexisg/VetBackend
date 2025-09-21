package com.Vet.VetBackend.historialvacuna.reportes.services; // Correct package

import com.Vet.VetBackend.historialvacuna.reportes.dto.ReporteVacunasDTO; // Ensure this line is correct

import java.time.LocalDate;
import java.util.List;

public interface ReporteVacunasService {
    List<ReporteVacunasDTO> generarReporte(LocalDate inicio, LocalDate fin);
}