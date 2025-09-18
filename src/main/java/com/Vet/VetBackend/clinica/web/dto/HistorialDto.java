package com.Vet.VetBackend.clinica.web.dto;

import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoRes;

import java.time.LocalDateTime;
import java.util.List;

public class HistorialDto {
    private Long id;
    private Long mascotaId;
    private Long citaId;
    private Long veterinarioId;
    private String diagnostico;
    private LocalDateTime creadoAt;
    // Aquí integramos la lista de tratamientos aplicados
    private List<TratamientoAplicadoRes> tratamientosAplicados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public LocalDateTime getCreadoAt() {
        return creadoAt;
    }

    public void setCreadoAt(LocalDateTime creadoAt) {
        this.creadoAt = creadoAt;
    }

    public List<TratamientoAplicadoRes> getTratamientosAplicados() {
        return tratamientosAplicados;
    }

    public void setTratamientosAplicados(List<TratamientoAplicadoRes> tratamientosAplicados) {
        this.tratamientosAplicados = tratamientosAplicados;
    }
}
