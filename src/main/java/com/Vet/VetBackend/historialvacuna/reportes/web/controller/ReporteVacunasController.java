package com.Vet.VetBackend.reportes.web.controller;

import com.Vet.VetBackend.reportes.dto.ReporteVacunasDTO;
import com.Vet.VetBackend.reportes.services.ReporteVacunasService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/reportes/vacunas")
@RequiredArgsConstructor
public class ReporteVacunasController {
    private final ReporteVacunasService reporteVacunasService;
    @GetMapping
    public ResponseEntity<List<ReporteVacunasDTO>> generarReporte(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(reporteVacunasService.generarReporte(start, end));
    }
}