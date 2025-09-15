// src/main/java/com/Vet/VetBackend/servicios/web/dto/MotivoServicioReq.java
package com.Vet.VetBackend.servicios.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MotivoServicioReq {
    @NotNull private Short motivoId;
    @NotNull private Long servicioId;
}
