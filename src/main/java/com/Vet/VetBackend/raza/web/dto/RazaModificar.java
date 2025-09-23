package com.Vet.VetBackend.raza.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter


public class RazaModificar  implements  Serializable{
    private  Integer id;
    private  String nombre;
    private Byte especieId;

}
