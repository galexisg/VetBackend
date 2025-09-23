// src/main/java/com/Vet/VetBackend/servicios/web/dto/ServicioReq.java
package com.Vet.VetBackend.servicios.web.dto;

import com.Vet.VetBackend.servicios.domain.EstadoServicio;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ServicioReq {
    @NotBlank
    private String nombre;
    private String descripcion;
    private BigDecimal precioBase;
    private EstadoServicio estado;
}