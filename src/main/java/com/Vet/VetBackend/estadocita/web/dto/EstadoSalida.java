package com.Vet.VetBackend.estadocita.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class EstadoSalida implements Serializable {
    private Integer id;
    private String nombre;

}
