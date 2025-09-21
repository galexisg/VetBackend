package com.Vet.VetBackend.historialvacuna.reportes.web.controller;

import com.Vet.VetBackend.historialvacuna.reportes.dto.ReporteVacunasDTO; // Import for DTO

import java.time.LocalDate;
import java.util.List;

public interface ReporteVacunasService {
    List<ReporteVacunasDTO> generarReporte(LocalDate start, LocalDate end);
}