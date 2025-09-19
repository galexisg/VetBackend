package com.Vet.VetBackend.consulta.web.dto;

import com.Vet.VetBackend.consulta.domain.Diagnostico;
import lombok.Data;

import java.util.List;

@Data
public class ConsultaDto {
    private Long id;
    private Long historialId;
    private String observaciones;
    private String recomendacions;
    private List<DiagnosticoDto> diagnosticos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHistorialId() {
        return historialId;
    }

    public void setHistorialId(Long historialId) {
        this.historialId = historialId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRecomendacions() {
        return recomendacions;
    }

    public void setRecomendacions(String recomendacions) {
        this.recomendacions = recomendacions;
    }

    public List<DiagnosticoDto> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(List<DiagnosticoDto> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }
}
