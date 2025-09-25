package com.Vet.VetBackend.historialvacuna.web.dto;

public class ReporteVacunasDTO {
    private String nombreVacuna;
    private long cantidadAplicada;
    private long mascotasAtendidas;

    public ReporteVacunasDTO() { }

    // habilita las llamadas antiguas new ReporteVacunasDTO(String,long,long)
    public ReporteVacunasDTO(String nombreVacuna, long cantidadAplicada, long mascotasAtendidas) {
        this.nombreVacuna = nombreVacuna;
        this.cantidadAplicada = cantidadAplicada;
        this.mascotasAtendidas = mascotasAtendidas;
    }

    // getters usados por Excel/PDF exporter
    public String getNombreVacuna() { return nombreVacuna; }
    public long getCantidadAplicada() { return cantidadAplicada; }
    public long getMascotasAtendidas() { return mascotasAtendidas; }

    // setters por si en otro lado lo llenas vía setters
    public void setNombreVacuna(String v) { this.nombreVacuna = v; }
    public void setCantidadAplicada(long v) { this.cantidadAplicada = v; }
    public void setMascotasAtendidas(long v) { this.mascotasAtendidas = v; }
}
