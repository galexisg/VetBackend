package com.Vet.VetBackend.Medicamento.app.services;

import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoGuardar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoModificar;
import com.Vet.VetBackend.Medicamento.web.dto.MedicamentoSalida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMedicamentoService {

    List<MedicamentoSalida> obtenerTodos();

    Page<MedicamentoSalida> obtenerTodosPaginados(Pageable pageable);

    MedicamentoSalida obtenerPorId(Integer id);

    MedicamentoSalida crear(MedicamentoGuardar medicamentoGuardar);

    MedicamentoSalida editar(MedicamentoModificar medicamentoSalida);

    void eliminarPorId(Integer id);
}

