package com.Vet.VetBackend.proveedores.web.dto;

import com.Vet.VetBackend.proveedores.domain.Proveedor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProveedorModificar implements Serializable {
    private Integer id;
    private String nombre;
    private String nit;
    private String telefono;
    private String email;
    private String direccion;
    private Integer estadoid;
    private String notas;

    // --- Convierte DTO a Entity ---
    public Proveedor toEntity() {
        Proveedor p = new Proveedor();
        p.setId(this.id); // importante para actualizar
        p.setNombre(this.nombre);
        p.setNit(this.nit);
        p.setTelefono(this.telefono);
        p.setEmail(this.email);
        p.setDireccion(this.direccion);
        p.setEstadoid(this.estadoid);
        p.setNotas(this.notas);
        return p;
    }
}
