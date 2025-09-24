package com.Vet.VetBackend.historialvacuna.web.controller;

import com.Vet.VetBackend.historialvacuna.web.dto.ReporteVacunasDTO;
import com.Vet.VetBackend.historialvacuna.reportes.services.ReporteVacunasService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes/vacunas")
public class ReporteVacunasController {

    private final ReporteVacunasService reporteVacunasService;

    public ReporteVacunasController(ReporteVacunasService reporteVacunasService) {
        this.reporteVacunasService = reporteVacunasService;
    }

    @GetMapping
    public ResponseEntity<List<ReporteVacunasDTO>> generarReporte(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        List<ReporteVacunasDTO> reportes = reporteVacunasService.generarReporte(start, end);
        return ResponseEntity.ok(reportes);
    }
}
