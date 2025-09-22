package com.Vet.VetBackend.vacuna.domain;

import com.Vet.VetBackend.veterinario.domain.Veterinario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "vacuna", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Vacuna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacuna_id")
    @EqualsAndHashCode.Include
    private Integer vacunaId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no debe exceder 80 caracteres")
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @PrePersist @PreUpdate
    private void normalize() {
        if (nombre != null) nombre = nombre.trim();
    }



}
