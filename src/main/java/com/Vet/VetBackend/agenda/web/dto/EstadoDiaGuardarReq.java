package com.Vet.VetBackend.agenda.web.dto;

import com.Vet.VetBackend.agenda.domain.EstadoDia;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDiaGuardarReq {

    @NotNull(message = "El estado no puede ser nulo")
    private EstadoDia.Estado estado;
}