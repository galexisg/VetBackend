package com.Vet.VetBackend.compras.app.services;

import com.Vet.VetBackend.compras.web.dto.CompraActualizar;
import com.Vet.VetBackend.compras.web.dto.CompraCancelar;
import com.Vet.VetBackend.compras.web.dto.CompraCrear;
import com.Vet.VetBackend.compras.web.dto.CompraObtener;
import java.time.LocalDate;
import java.util.List;

public interface CompraService {
    CompraObtener crear(CompraCrear compraCrear);
    CompraObtener obtenerPorId(Long id);
    List<CompraObtener> obtenerTodos();
    CompraObtener actualizar(Long id, CompraActualizar compraActualizar);
    void cancelar(Long id, CompraCancelar dto);
    List<CompraObtener> obtenerPorProveedor(Integer proveedorId);
    List<CompraObtener> obtenerPorFecha(LocalDate fecha);
    List<CompraObtener> obtenerPorUsuario(Integer usuarioId);
}
