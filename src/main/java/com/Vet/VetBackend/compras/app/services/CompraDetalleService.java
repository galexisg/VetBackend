package com.Vet.VetBackend.compras.app.services;

import com.Vet.VetBackend.compras.web.dto.ActualizarDetalle;
import com.Vet.VetBackend.compras.web.dto.CancelarDetalle;
import com.Vet.VetBackend.compras.web.dto.CrearDetalle;
import com.Vet.VetBackend.compras.web.dto.ObtenerDetalle;

import java.util.List;

public interface CompraDetalleService {

    List<ObtenerDetalle> compra_detalle();

    ObtenerDetalle detalle_por_id(Long id);

    ObtenerDetalle agregar_detalle(CrearDetalle dto);

    ObtenerDetalle actualizar_detalle(Long id, ActualizarDetalle dto);

    void eliminar_detalle(Long id, CancelarDetalle dto);

    List<ObtenerDetalle> detalles_por_compra(Long compraId);
}
