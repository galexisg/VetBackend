package com.Vet.VetBackend.clinica.web.dto;

import com.Vet.VetBackend.consulta.web.dto.ConsultaDto;
import com.Vet.VetBackend.tratamientos.web.dto.TratamientoAplicadoRes;

import java.time.LocalDateTime;
import java.util.List;

public class HistorialDto {
    private Long id;
    private Integer mascotaId;
    private Long citaId;
    private Integer veterinarioId;
    private LocalDateTime creadoAt;
    private List<TratamientoAplicadoRes> tratamientosAplicados;
    private List<ConsultaDto> consultas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Integer mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public Integer getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Integer veterinarioId) {
        this.veterinarioId = veterinarioId;
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
