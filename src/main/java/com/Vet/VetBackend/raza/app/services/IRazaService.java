package com.Vet.VetBackend.raza.app.services;

import com.Vet.VetBackend.raza.web.dto.RazaGuardar;
import com.Vet.VetBackend.raza.web.dto.RazaModificar;
import com.Vet.VetBackend.raza.web.dto.RazaSalida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRazaService {

    List<RazaSalida> obtenerTodos();

    Page<RazaSalida> obtenerTodosPaginados(Pageable pageable);

    RazaSalida obtenerPorId(Integer id);

    RazaSalida crear(RazaGuardar razaGuardar);

    RazaSalida editar(RazaModificar razaModificar);

    void eliminarPorId(Integer id);
}