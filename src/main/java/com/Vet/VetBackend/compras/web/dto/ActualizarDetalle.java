package com.Vet.VetBackend.compras.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarDetalle implements Serializable {
    private Long productoId;
    private int cantidad;
    private double precio;
}
