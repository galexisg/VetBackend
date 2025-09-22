package com.Vet.VetBackend.citas.web.dto;

import com.Vet.VetBackend.citas.domain.Cita;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CitaRequestDTO {

    private int mascotaId;
    private int usuarioId;
    private int veterinarioId;
    private int motivoId;
    private int citaEstadoId;
    private Long historialId;
    private Long facturaId;
    private Long consultaId;

    private Cita.Tipo tipo = Cita.Tipo.Normal;

    @NotNull(message = "fechaHora es obligatoria")
    private LocalDateTime fechaHora;

    @Size(max = 250, message = "observaciones no puede exceder 250 caracteres")
    private String observaciones;

    // --- Getters y Setters ---
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

    public void setCitaEstadoId( int citaEstadoId) {
        this.citaEstadoId = citaEstadoId;
    }

    public Long getHistorialId() {
        return historialId;
    }

    public void setHistorialId(Long historialId) {
        this.historialId = historialId;
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
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