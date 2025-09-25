// src/main/java/com/Vet/VetBackend/servicios/web/dto/ServicioRes.java
package com.Vet.VetBackend.servicios.web.dto;

import com.Vet.VetBackend.servicios.domain.EstadoServicio;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ServicioRes {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioBase;
    private EstadoServicio estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}