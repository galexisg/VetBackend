package com.Vet.VetBackend.compras.web.dto;


import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraObtener implements Serializable {
    private Integer id;
    private Long proveedorId;
    // private Long usuarioId; // 🔜 Se mostrará cuando esté disponible
    private LocalDate fecha;
    private String descripcion;
    private double total;
}