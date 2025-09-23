package com.Vet.VetBackend.usuarios.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Usuario_Salida implements Serializable {
    private Integer id;
    private String nombreCompleto;
    private String correo;
    private String nickName;
}
