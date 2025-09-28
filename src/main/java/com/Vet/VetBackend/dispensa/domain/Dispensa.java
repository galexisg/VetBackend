package com.Vet.VetBackend.dispensa.domain;

import com.Vet.VetBackend.almacen.domain.Almacen;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dispensa")
public class Dispensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime fecha;

    @Column(name = "lote_id")
    private Integer loteId;

    @Column(name = "prescripcion_detalle_id")
    private Integer prescripcionDetalleId;

    // 🔹 Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id") // El campo en la tabla es usuario_id, y la PK en Usuario también
    private Usuario usuario;

    // 🔹 Relación con Almacén
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "almacen_id", referencedColumnName = "id") // 🔧 Aquí estaba el error
    private Almacen almacen;
}
