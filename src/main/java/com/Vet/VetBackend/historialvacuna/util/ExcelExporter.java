package com.Vet.VetBackend.historialvacuna.util; // Ensure the package is correct

import com.Vet.VetBackend.historialvacuna.app.web.dto.ReporteVacunasDTO; // Correct import

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public static ByteArrayInputStream exportarReporteVacunas(List<ReporteVacunasDTO> reportes) throws IOException {
        String[] columnas = {"Vacuna", "Cantidad Aplicada", "Mascotas Atendidas"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet hoja = workbook.createSheet("Reporte de Vacunas");

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            Row headerRow = hoja.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (ReporteVacunasDTO r : reportes) {
                Row row = hoja.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getNombreVacuna());
                row.createCell(1).setCellValue(r.getCantidadAplicada());
                row.createCell(2).setCellValue(r.getMascotasAtendidas());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}