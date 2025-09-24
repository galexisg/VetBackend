package com.Vet.VetBackend.agenda.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;
import jakarta.validation.constraints.AssertTrue;

@Entity
@Table(name = "bloque_horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloqueHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bloque_horario_id", nullable = false)
    private Integer bloqueHorarioId;

    @Column(name = "inicio", columnDefinition = "TIME(6)", nullable = false)
    private LocalTime inicio;

    @Column(name = "fin", columnDefinition = "TIME(6)", nullable = false)
    private LocalTime fin;

    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;



    // 🔹 Validación extra para reflejar el CHECK (inicio < fin)
    @AssertTrue(message = "La hora de inicio debe ser menor que la hora de fin")
    public boolean isRangoValido() {
        return inicio != null && fin != null && inicio.isBefore(fin);
    }
}
