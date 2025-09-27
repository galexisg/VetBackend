package com.Vet.VetBackend.proveedores.web.dto;

import com.Vet.VetBackend.proveedores.domain.Proveedor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProveedorSalida implements Serializable {
    private Integer id;
    private String nombre;
    private String nit;
    private String telefono;
    private String email;
    private String direccion;
    private Integer estadoid;
    private String notas;

    public static ProveedorSalida fromEntity(Proveedor p) {
        ProveedorSalida dto = new ProveedorSalida();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setNit(p.getNit());
        dto.setTelefono(p.getTelefono());
        dto.setEmail(p.getEmail());
        dto.setDireccion(p.getDireccion());
        dto.setEstadoid(p.getEstadoid());
        dto.setNotas(p.getNotas());
        return dto;
    }
}
