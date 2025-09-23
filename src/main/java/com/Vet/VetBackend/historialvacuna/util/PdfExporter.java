package com.Vet.VetBackend.historialvacuna.util;

import com.Vet.VetBackend.historialvacuna.web.dto.ReporteVacunasDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfExporter {

    public static ByteArrayInputStream exportarReporteVacunas(List<ReporteVacunasDTO> reportes) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Create the PDF writer instance
            PdfWriter.getInstance(document, out);
            document.open();

            // Create a title font
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("📋 Reporte de Vacunas", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Create a table with three columns
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new int[] { 4, 2, 2 });
            tabla.setSpacingBefore(10);

            // Create header cells
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            addTableHeader(tabla, headerFont);

            // Add data rows
            for (ReporteVacunasDTO r : reportes) {
                tabla.addCell(r.getNombreVacuna());
                tabla.addCell(String.valueOf(r.getCantidadAplicada()));
                tabla.addCell(String.valueOf(r.getMascotasAtendidas()));
            }

            // Add the table to the document
            document.add(tabla);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addTableHeader(PdfPTable table, Font headerFont) {
        PdfPCell header;

        header = new PdfPCell(new Phrase("Vacuna", headerFont));
        header.setBackgroundColor(BaseColor.DARK_GRAY);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        header = new PdfPCell(new Phrase("Cantidad Aplicada", headerFont));
        header.setBackgroundColor(BaseColor.DARK_GRAY);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        header = new PdfPCell(new Phrase("Mascotas Atendidas", headerFont));
        header.setBackgroundColor(BaseColor.DARK_GRAY);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }
}