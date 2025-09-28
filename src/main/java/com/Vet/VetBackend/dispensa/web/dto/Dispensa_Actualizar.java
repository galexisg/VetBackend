package com.Vet.VetBackend.dispensa.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

@Getter
@Setter
public class Dispensa_Actualizar implements Serializable {
    private Integer almacenId;           // ID del almacén a actualizar
    private Integer loteId;              // ID del lote
    private BigDecimal cantidad;         // Cantidad a actualizar
    private LocalDateTime fecha;         // Fecha de actualización
    private Integer prescripcionDetalleId;
    private Integer usuarioId;

    // ✅ Opcional: solo para mostrar (no se mapean en la actualización del backend)
    private String almacenNombre;        // Nombre del almacén
    private String usuarioNombre;        // Nombre del usuario
}
