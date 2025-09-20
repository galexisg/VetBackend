package com.Vet.VetBackend.compras.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObtenerDetalle implements Serializable {
    private Integer id;
    private Integer compraId;
    private Integer productoId;
    private int cantidad;
    private double precio;
}
