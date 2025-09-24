package com.Vet.VetBackend.compras.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "compra_detalle")
public class CompraDetalle {

    // Getters y setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "compra_id")  // mapea la columna compra_id de la tabla
    private Long compraId;

    private Integer cantidad;
    private Double precio;

    public void setId(Long id) { this.id = id; }

    public void setCompraId(Long compraId) { this.compraId = compraId; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public void setPrecio(Double precio) { this.precio = precio; }
}
