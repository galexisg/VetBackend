package com.Vet.VetBackend.proveedores.app.services;

import com.Vet.VetBackend.proveedores.web.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProveedorService {
    List<ProveedorSalida> obtenerTodos();
    Page<ProveedorSalida> obtenerTodosPaginados(Pageable pageable);
    ProveedorSalida obtenerPorId(Integer id);
    ProveedorSalida crear(ProveedorGuardar proveedorGuardar);
    ProveedorSalida editar(ProveedorModificar proveedorModificar);
    void eliminarPorId(Integer id);
}
