package com.Vet.VetBackend.compras.web.controller;

import com.Vet.VetBackend.compras.app.services.CompraService;
import com.Vet.VetBackend.compras.web.dto.CompraActualizar;
import com.Vet.VetBackend.compras.web.dto.CompraCancelar;
import com.Vet.VetBackend.compras.web.dto.CompraCrear;
import com.Vet.VetBackend.compras.web.dto.CompraObtener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraObtener>> obtenerTodas() {
        List<CompraObtener> compras = compraService.obtenerTodos();
        return compras.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraObtener> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CompraObtener> crear(@RequestBody CompraCrear dto) {
        return ResponseEntity.ok(compraService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraObtener> actualizar(
            @PathVariable Long id,
            @RequestBody CompraActualizar dto
    ) {
        return ResponseEntity.ok(compraService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(
            @PathVariable Long id,
            @RequestBody CompraCancelar dto
    ) {
        compraService.cancelar(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<CompraObtener>> obtenerPorProveedor(
            @PathVariable Integer proveedorId
    ) {
        List<CompraObtener> compras = compraService.obtenerPorProveedor(proveedorId);
        return compras.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(compras);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CompraObtener>> obtenerPorFecha(
            @PathVariable String fecha
    ) {
        LocalDate date = LocalDate.parse(fecha);
        List<CompraObtener> compras = compraService.obtenerPorFecha(date);
        return compras.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(compras);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CompraObtener>> obtenerPorUsuario(
            @PathVariable Integer usuarioId
    ) {
        List<CompraObtener> compras = compraService.obtenerPorUsuario(usuarioId);
        return compras.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(compras);
    }
}
