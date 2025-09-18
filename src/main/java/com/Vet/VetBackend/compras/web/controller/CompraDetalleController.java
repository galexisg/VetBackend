package com.Vet.VetBackend.compras.web.controller;

import com.Vet.VetBackend.compras.app.services.CompraDetalleService;
import com.Vet.VetBackend.compras.web.dto.ActualizarDetalle;
import com.Vet.VetBackend.compras.web.dto.CancelarDetalle;
import com.Vet.VetBackend.compras.web.dto.CrearDetalle;
import com.Vet.VetBackend.compras.web.dto.ObtenerDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras-detalle")
public class CompraDetalleController {

    @Autowired
    private CompraDetalleService detalleService;

    @GetMapping
    public List<ObtenerDetalle> listarDetalles() {
        return detalleService.compra_detalle();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerDetalle> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(detalleService.detalle_por_id(id));
    }

    @PostMapping
    public ObtenerDetalle crearDetalle(@RequestBody CrearDetalle dto) {
        return detalleService.agregar_detalle(dto);
    }

    @PutMapping("/{id}")
    public ObtenerDetalle actualizarDetalle(@PathVariable Long id, @RequestBody ActualizarDetalle dto) {
        return detalleService.actualizar_detalle(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalle(@PathVariable Long id, @RequestBody CancelarDetalle dto) {
        detalleService.eliminar_detalle(id, dto);
    }

    @GetMapping("/compra/{compraId}")
    public List<ObtenerDetalle> detallesPorCompra(@PathVariable Long compraId) {
        return detalleService.detalles_por_compra(compraId);
    }
}
