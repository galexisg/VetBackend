package com.Vet.VetBackend.compras.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class CompraObtener implements Serializable {
    private Integer id;
    private Integer proveedorId;
    private Integer usuarioId;
    private LocalDate fecha;
    private String descripcion;
    private double total;
}
