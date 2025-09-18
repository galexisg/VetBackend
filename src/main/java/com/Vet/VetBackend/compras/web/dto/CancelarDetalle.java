package com.Vet.VetBackend.compras.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelarDetalle implements Serializable {
    private String motivo; // Motivo de cancelación
}
