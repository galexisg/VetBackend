package com.Vet.VetBackend.historialvacuna.app.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_vacuna") // Nombre de la tabla en la base de datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialVacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historialvacunaid")
    private Integer historialVacunaId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idvacuna", nullable = false)
    private com.Vet.VetBackend.vacuna.domain.Vacuna vacuna;

    @Column(name = "idmascota", nullable = false)
    private Integer mascotaId;

    @Column(name = "idveterinario", nullable = false)
    private Integer veterinarioId;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "medicamentoid")
    private Integer medicamentoId;

    @Column(name = "loteid")
    private Integer loteId;

    @Column(name = "observacion", length = 200)
    private String observacion;
}
