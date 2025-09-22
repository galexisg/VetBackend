package com.Vet.VetBackend.citas.web.dto;

import com.Vet.VetBackend.citas.domain.Cita;
import java.time.LocalDateTime;

public class CitaResponseDTO {
    private Long citaId;
    private Integer mascotaId;
    private Integer usuarioId;
    private Short veterinarioId;
    private Byte motivoId;
    private int citaEstadoId;
    private Long historialId;
    private Long facturaId;
    private Long consultaId;
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

    public Integer getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Integer mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Short getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Short veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public Byte getMotivoId() {
        return motivoId;
    }

    public void setMotivoId(Byte motivoId) {
        this.motivoId = motivoId;
    }

    public int getCitaEstadoId() {
        return citaEstadoId;
    }

    public void setCitaEstadoId(int citaEstadoId) {
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