package com.Vet.VetBackend.raza.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter


public class RazaModificar  implements  Serializable{
    private  Byte id;
    private  String nombre;
    private Byte especieId;
    private String especieNombre;

}
