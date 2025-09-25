package com.Vet.VetBackend.servicios.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "motivo",
        uniqueConstraints = @UniqueConstraint(name = "uk_motivo_nombre", columnNames = "nombre")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Motivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Autogenera el ID
    @Column(columnDefinition = "TINYINT")
    private Short id;

    @Column(nullable = false, length = 60)
    private String nombre;
}
