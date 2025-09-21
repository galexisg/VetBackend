package com.Vet.VetBackend.historialvacuna.reportes.implementations;

import com.Vet.VetBackend.historialvacuna.reportes.dto.ReporteVacunasDTO;
import com.Vet.VetBackend.historialvacuna.reportes.services.ReporteVacunasService;
import com.Vet.VetBackend.reportes.dto.ReporteVacunasDTO;
import com.Vet.VetBackend.reportes.services.ReporteVacunasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ReporteVacunasImpl implements ReporteVacunasService.ReporteVacunasService {
    @Override
    public List<ReporteVacunasDTO> generarReporte(LocalDate inicio, LocalDate fin) {
        // TODO: Implementar consulta real
        return List.of();
    }
}