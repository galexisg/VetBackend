package com.Vet.VetBackend.compras.domain;

import com.Vet.VetBackend.proveedores.domain.Proveedor;
import com.Vet.VetBackend.usuarios.domain.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "total", nullable = false)
    private double total;

    @ManyToOne
    @JoinColumn(
            name = "proveedor_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_compra__proveedor")
    )
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(
            name = "usuario_id",
            referencedColumnName = "UsuarioId",
            nullable = true,
            foreignKey = @ForeignKey(name = "fk_compra_usuario")
    )
    private Usuario usuario;
}
