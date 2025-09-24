package com.Vet.VetBackend.citas.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cita_id")
    private Long citaId;

    @Column(name = "mascota_id", nullable = true)
    private int mascotaId;

    @Column(name = "usuario_id", nullable = true)
    private int usuarioId;

    @Column(name = "veterinario_id", nullable = true)
    private int veterinarioId;

    @Column(name = "motivo_id", nullable = true)
    private int motivoId;

    @Column(name = "cita_estado_id", nullable = true)
    private Integer citaEstadoId;


    @Column(name = "factura_id", nullable = true)
    private Long facturaId;

    public enum Tipo {
        Normal, Emergencia, Control
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, columnDefinition = "ENUM('Normal','Emergencia','Control') DEFAULT 'Normal'")
    private Tipo tipo = Tipo.Normal;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "observaciones", length = 250, nullable = true)
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
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