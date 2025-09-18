package com.Vet.VetBackend.proveedores.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProveedorGuardar implements Serializable{
    private String nombre;
    private String nit;
    private String telefono;
    private String email;
    private String direccion;
    private Integer estadoid;  // 👈 Campo agregado, puede ser NULL
    private String notas;

}
