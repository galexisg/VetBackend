// src/main/java/com/Vet/VetBackend/servicios/web/dto/ServicioEstadoReq.java
package com.Vet.VetBackend.servicios.web.dto;

import com.Vet.VetBackend.servicios.domain.EstadoServicio;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServicioEstadoReq {
    @NotNull
    private EstadoServicio estado;
}
