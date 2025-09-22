package com.Vet.VetBackend.agenda.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaModificarReq {

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 20, message = "El nombre no puede exceder los 20 caracteres")
    private String nombre;

    @NotNull(message = "El estado del día no puede ser nulo")
    private String estadoDia; // Igual que en DiaGuardarReq
}
