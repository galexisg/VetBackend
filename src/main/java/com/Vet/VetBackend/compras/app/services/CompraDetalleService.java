package com.Vet.VetBackend.compras.app.services;


import com.Vet.VetBackend.compras.web.dto.ObtenerDetalle;
import com.Vet.VetBackend.compras.web.dto.CancelarDetalle;
import com.Vet.VetBackend.compras.web.dto.CrearDetalle;
import com.Vet.VetBackend.compras.web.dto.ActualizarDetalle;

import java.util.List;

public interface CompraDetalleService {

    List<ObtenerDetalle> compradetalle();

    ObtenerDetalle detalle_por_id(Long id);

    ObtenerDetalle agregardetalle(CrearDetalle dto);

    ObtenerDetalle actualizardetalle(Long id, ActualizarDetalle dto);

    void eliminardetalle(Long id, CancelarDetalle dto);

    List<ObtenerDetalle> detalles_por_compra(Long compraId);
}
