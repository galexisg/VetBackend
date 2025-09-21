package com.Vet.VetBackend.historialvacuna.reportes.services;

import com.Vet.VetBackend.historialvacuna.reportes.dto.ReporteVacunasDTO;
import java.time.LocalDate;
import java.util.List;

public interface ReporteVacunasService {
    List<ReporteVacunasDTO> generarReporte(LocalDate inicio, LocalDate fin);


}