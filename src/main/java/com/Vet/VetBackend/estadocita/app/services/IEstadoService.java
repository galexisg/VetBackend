package com.Vet.VetBackend.estadocita.app.services;

import com.Vet.VetBackend.estadocita.web.dto.EstadoGuardar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoModificar;
import com.Vet.VetBackend.estadocita.web.dto.EstadoSalida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEstadoService {

    List<EstadoSalida> obtenerTodos();

    Page<EstadoSalida> obtenerTodosPaginados(Pageable pageable);

    EstadoSalida obtenerPorId(Integer id);

    EstadoSalida crear(EstadoGuardar estadoGuardar);

    EstadoSalida editar(EstadoModificar estadoModificar);


}
