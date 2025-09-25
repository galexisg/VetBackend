package com.Vet.VetBackend.historialvacuna.app.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_vacuna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialVacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historial_vacuna_id")   // <-- PK exacta en BD
    private Integer historialVacunaId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vacuna_id", nullable = false)  // <-- FK exacta en BD
    private com.Vet.VetBackend.vacuna.domain.Vacuna vacuna;

    @Column(name = "mascota_id", nullable = false)
    private Integer mascotaId;

    @Column(name = "veterinario_id", nullable = false)
    private Integer veterinarioId;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "medicamento_id")        // nullable por defecto = true
    private Integer medicamentoId;

    @Column(name = "lote_medicamento_id")   // <-- nombre real en BD
    private Integer loteId;

    @Column(name = "observacion", length = 200)
    private String observacion;
}
