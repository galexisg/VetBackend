package com.Vet.VetBackend.mascotas.app.services;

import com.Vet.VetBackend.mascotas.domain.Mascota;
import java.util.List;

public interface MascotaService {
    Mascota crear(Mascota mascota);
    Mascota actualizar(Integer id, Mascota mascota);
    Mascota obtenerPorId(Integer id);
    List<Mascota> listarTodos();
}

