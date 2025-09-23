package com.Vet.VetBackend.estadocita.web.dto;

import com.Vet.VetBackend.estadocita.domain.EstadoCitaEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EstadoGuardar implements Serializable {
    private EstadoCitaEnum nombre;
}


