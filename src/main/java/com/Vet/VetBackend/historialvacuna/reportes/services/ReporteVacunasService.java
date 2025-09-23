package com.Vet.VetBackend.historialvacuna.reportes.services;

import com.Vet.VetBackend.historialvacuna.web.dto.ReporteVacunasDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteVacunasService {
    List<ReporteVacunasDTO> generarReporte(LocalDate start, LocalDate end);
}