package com.Vet.VetBackend.proveedores.web.dto;

import com.Vet.VetBackend.proveedores.domain.Proveedor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProveedorGuardar implements Serializable {
    private String nombre;
    private String nit;
    private String telefono;
    private String email;
    private String direccion;
    private Integer estadoid;
    private String notas;

    public Proveedor toEntity() {
        Proveedor p = new Proveedor();
        p.setNombre(nombre);
        p.setNit(nit);
        p.setTelefono(telefono);
        p.setEmail(email);
        p.setDireccion(direccion);
        p.setEstadoid(estadoid);
        p.setNotas(notas);
        return p;
    }
}
