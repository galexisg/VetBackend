package com.Vet.VetBackend.historialvacuna.util; // Ensure the package declaration is correct

import com.Vet.VetBackend.historialvacuna.reportes.dto.ReporteVacunasDTO; // Correct import

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
            PdfWriter.getInstance(document, out);
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("📋 Reporte de Vacunas", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new int[]{4, 2, 2});
            tabla.setSpacingBefore(10);

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            PdfPCell header;

            header = new PdfPCell(new Phrase("Vacuna", headerFont));
            header.setBackgroundColor(BaseColor.DARK_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(header);

            header = new PdfPCell(new Phrase("Cantidad Aplicada", headerFont));
            header.setBackgroundColor(BaseColor.DARK_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(header);

            header = new PdfPCell(new Phrase("Mascotas Atendidas", headerFont));
            header.setBackgroundColor(BaseColor.DARK_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(header);

            for (ReporteVacunasDTO r : reportes) {
                tabla.addCell(r.getNombreVacuna());
                tabla.addCell(String.valueOf(r.getCantidadAplicada()));
                tabla.addCell(String.valueOf(r.getMascotasAtendidas()));
            }

            document.add(tabla);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}