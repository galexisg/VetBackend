package com.Vet.VetBackend.almacen.app.services;

import  com.Vet.VetBackend.almacen.web.dto.Almacen_Actualizar;
import  com.Vet.VetBackend.almacen.web.dto.Almacen_CambiarEstado;
import  com.Vet.VetBackend.almacen.web.dto.Almacen_Guardar;
import  com.Vet.VetBackend.almacen.web.dto.Almacen_Salida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IAlmacenService {
    List<Almacen_Salida> obtenerTodos();

    Page<Almacen_Salida> obtenerTodosPaginados(Pageable pageable);

    Almacen_Salida obtenerPorId(Integer id);

    Almacen_Salida crear(Almacen_Guardar almacenGuardar);

    Almacen_Salida editar(Integer id, Almacen_Actualizar almacenActualizar);

    void eliminarPorId(Integer id);

    Almacen_Salida cambiarEstado(Integer id, Almacen_CambiarEstado almacenCambiarEstado);
}
