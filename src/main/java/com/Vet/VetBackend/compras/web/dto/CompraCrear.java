package com.Vet.VetBackend.compras.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class CompraCrear implements Serializable {
    private Integer proveedorId;
    private Integer usuarioId;
    private LocalDate fecha;
    private String descripcion;
    private double total;
}

