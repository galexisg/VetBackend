// src/main/java/com/Vet/VetBackend/historialvacuna/web/dto/CreateHistorialVacunaDTO.java
package com.Vet.VetBackend.historialvacuna.web.dto;

public class CreateHistorialVacunaDTO {
    private Long mascotaId;
    private Long vacunaId;
    private java.time.LocalDate fecha;
    private String observaciones;

    public CreateHistorialVacunaDTO() {}

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long v) { this.mascotaId = v; }

    public Long getVacunaId() { return vacunaId; }
    public void setVacunaId(Long v) { this.vacunaId = v; }

    public java.time.LocalDate getFecha() { return fecha; }
    public void setFecha(java.time.LocalDate f) { this.fecha = f; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String o) { this.observaciones = o; }
}
