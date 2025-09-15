// src/main/java/com/Vet/VetBackend/servicios/web/dto/ServicioRes.java
package com.Vet.VetBackend.servicios.web.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ServicioRes {
    private Long id; private String nombre; private String descripcion;
    private BigDecimal precioBase; private Boolean activo;
    private LocalDateTime createdAt; private LocalDateTime updatedAt;
}
