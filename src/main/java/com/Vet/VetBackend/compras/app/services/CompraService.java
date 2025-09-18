package com.Vet.VetBackend.compras.app.services;

import com.Vet.VetBackend.compras.web.dto.CompraCancelar;
import com.Vet.VetBackend.compras.web.dto.CompraCrear;
import com.Vet.VetBackend.compras.web.dto.CompraActualizar;
import com.Vet.VetBackend.compras.web.dto.CompraObtener;

import java.time.LocalDate;
import java.util.List;

public interface CompraService {

    // Crear una nueva compra
    CompraObtener crear(CompraCrear compraCrear);

    // Obtener una compra específica por ID
    CompraObtener obtenerPorId(Integer id);

    // Listar todas las compras
    List<CompraObtener> obtenerTodos();

    // Actualizar una compra existente
    CompraObtener actualizar(Integer id, CompraActualizar compraActualizar);

    // Cancelar una compra por ID
    void cancelar(Integer id, CompraCancelar dto);

    // ✅ Métodos adicionales que ya tienes implementados
    List<CompraObtener> obtenerPorProveedor(Integer proveedorId);

    List<CompraObtener> obtenerPorFecha(LocalDate fecha);

    List<CompraObtener> obtenerPorUsuario(Integer usuarioId);
}
