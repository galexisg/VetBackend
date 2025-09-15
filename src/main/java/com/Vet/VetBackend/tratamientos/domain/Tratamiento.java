
package com.Vet.VetBackend.tratamientos.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tratamiento")
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")            // ← doc: id
    private Long id;

    @Column(name = "nombre", nullable = false, length = 120, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "duracion_dias")
    private Integer duracionDias;

    @Column(name = "frecuencia", length = 60)
    private String frecuencia;

    @Column(name = "via", length = 60)
    private String via;

    @Column(name = "activo")
    private Boolean activo = true;
}
