package com.Vet.VetBackend.compras.web.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraCrear implements Serializable {
    private Long proveedorId;
    private Long usuarioId;
    private LocalDate fecha;
    private String descripcion;
    private double total;
}