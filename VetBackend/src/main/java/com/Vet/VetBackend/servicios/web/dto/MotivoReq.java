// src/main/java/com/Vet/VetBackend/servicios/web/dto/MotivoReq.java
package com.Vet.VetBackend.servicios.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MotivoReq {
    @NotNull private Short id;
    @NotBlank @Size(max = 60) private String nombre;
}
