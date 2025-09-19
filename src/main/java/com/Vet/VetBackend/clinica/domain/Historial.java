package com.Vet.VetBackend.clinica.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historial_id")
    private Long id;

    @Column(name = "mascota_id", nullable = false)
    private Integer mascotaId;

    @Column(name = "cita_id", nullable = false)
    private Long citaId;

    @Column(name = "veterinario_id", nullable = false)
    private Integer veterinarioId;

    @Column(name = "diagnostico", length = 500, nullable = false)
    private String diagnostico;

    @Column(name = "creado_at", nullable = false, updatable = false, insertable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime creadoAt;

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
}