package com.Vet.VetBackend.usuarios.app.services;

import com.Vet.VetBackend.usuarios.domain.Estado;
import java.util.List;

public interface EstadoService {
    Estado obtenerPorId(Byte id);
    List<Estado> listar();
}