package com.Vet.VetBackend.usuarios.app.services;

import com.Vet.VetBackend.usuarios.domain.Rol;
import java.util.List;

public interface RolService {
    Rol obtenerPorId(Byte id);
    List<Rol> listar();
}
