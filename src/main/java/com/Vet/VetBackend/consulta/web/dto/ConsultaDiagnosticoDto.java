package com.Vet.VetBackend.consulta.web.dto;

public class ConsultaDiagnosticoDto {
    private Long Id;
    private Long consultaId;
    private Long diagnosticoId;
    private String diagnosticoNombre;
    private String observacion;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }

    public Long getDiagnosticoId() {
        return diagnosticoId;
    }

    public void setDiagnosticoId(Long diagnoticoId) {
        this.diagnosticoId = diagnoticoId;
    }

    public String getDiagnosticoNombre() {
        return diagnosticoNombre;
    }

    public void setDiagnosticoNombre(String diagnosticoNombre) {
        this.diagnosticoNombre = diagnosticoNombre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
