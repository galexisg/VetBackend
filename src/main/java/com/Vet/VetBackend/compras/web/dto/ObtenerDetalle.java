package com.Vet.VetBackend.compras.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObtenerDetalle implements Serializable {
    private Long id;
    private Long compraId;
    private int cantidad;
    private double precio;

    // 🔴 productoId eliminado
}

