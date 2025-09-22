package com.Vet.VetBackend.historialvacuna.app.web.controller;

import com.Vet.VetBackend.historialvacuna.app.web.dto.ReporteVacunasDTO;
import com.Vet.VetBackend.historialvacuna.reportes.services.ReporteVacunasService;
import com.Vet.VetBackend.historialvacuna.app.web.dto.ReporteVacunasDTO;
import com.Vet.VetBackend.historialvacuna.util.PdfExporter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
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
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generarReportePdf(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        List<ReporteVacunasDTO> reportes = reporteVacunasService.generarReporte(start, end);

        ByteArrayInputStream bis = PdfExporter.exportarReporteVacunas(reportes);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=reporte_vacunas.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

}