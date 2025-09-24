package com.Vet.VetBackend.compras.web.controller;

import com.Vet.VetBackend.compras.app.services.CompraDetalleService;
import com.Vet.VetBackend.compras.web.dto.ActualizarDetalle;
import com.Vet.VetBackend.compras.web.dto.CancelarDetalle;
import com.Vet.VetBackend.compras.web.dto.CrearDetalle;
import com.Vet.VetBackend.compras.web.dto.ObtenerDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/compra-detalle")
public class CompraDetalleController {

    @Autowired
    private CompraDetalleService detalleService;

    @GetMapping
    public List<ObtenerDetalle> listarDetalles() {
        return detalleService.compra_detalle();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerDetalle> obtenerDetalle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(detalleService.detalle_por_id(id));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada", e);
        }
    }

    @PostMapping
    public ObtenerDetalle crearDetalle(@RequestBody CrearDetalle dto) {
        return detalleService.agregar_detalle(dto);
    }

    @PutMapping("/{id}")
    public ObtenerDetalle actualizarDetalle(@PathVariable Long id, @RequestBody ActualizarDetalle dto) {
        try {
            return detalleService.actualizar_detalle(id, dto);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada", e);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalle(@PathVariable Long id, @RequestBody CancelarDetalle dto) {
        try {
            detalleService.eliminar_detalle(id, dto);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada", e);
        }
    }

    @GetMapping("/compra/{compraId}")
    public List<ObtenerDetalle> detallesPorCompra(@PathVariable Long compraId) {
        try {
            return detalleService.detalles_por_compra(compraId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada", e);
        }
    }
}
