package com.Vet.VetBackend.lote_medicamentos.domain;

import com.Vet.VetBackend.Medicamento.domain.Medicamento;
import com.Vet.VetBackend.proveedores.domain.Proveedor;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "lote_medicamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lotes_medicamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_lote", length = 60)
    private String codigoLote;

    @Column(name = "fecha_vencimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "observaciones", length = 200)
    private String observaciones;

    // --- Relaciones ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;
}
