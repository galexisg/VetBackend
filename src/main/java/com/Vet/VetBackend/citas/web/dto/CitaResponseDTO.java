package com.Vet.VetBackend.citas.web.dto;

import com.Vet.VetBackend.citas.domain.Cita;
import java.time.LocalDateTime;

public class CitaResponseDTO {
    private Long citaId;
    private int mascotaId;
    private int usuarioId;
    private int veterinarioId;
    private int motivoId;
    private int citaEstadoId;
    private Long facturaId;
    private Cita.Tipo tipo;
    private LocalDateTime fechaHora;
    private String observaciones;

    // --- Getters y Setters ---
    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public int getMotivoId() {
        return motivoId;
    }

    public void setMotivoId(int motivoId) {
        this.motivoId = motivoId;
    }

    public int getCitaEstadoId() {
        return citaEstadoId;
    }

    public void setCitaEstadoId(int citaEstadoId) {
        this.citaEstadoId = citaEstadoId;
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Cita.Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Cita.Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}