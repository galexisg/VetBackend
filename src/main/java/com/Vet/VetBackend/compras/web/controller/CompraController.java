package com.Vet.VetBackend.compras.web.controller;

import com.Vet.VetBackend.compras.domain.Compra;
import com.Vet.VetBackend.compras.repo.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraRepository compraRepository;

    // GET /api/compras - Listar todas las compras
    @GetMapping
    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    // GET /api/compras/{id} - Obtener compra por ID
    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable Integer id) {
        return compraRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/compras - Crear compra
    @PostMapping
    public Compra crearCompra(@RequestBody Compra compra) {
        return compraRepository.save(compra);
    }

    // PUT /api/compras/{id} - Actualizar compra
    @PutMapping("/{id}")
    public ResponseEntity<Compra> actualizarCompra(@PathVariable Integer id, @RequestBody Compra compraDetails) {
        return compraRepository.findById(id)
                .map(compra -> {
                    compra.setProveedorId(compraDetails.getProveedorId());
                    compra.setUsuarioId(compraDetails.getUsuarioId());
                    compra.setFecha(compraDetails.getFecha());
                    compra.setDescripcion(compraDetails.getDescripcion());
                    compra.setTotal(compraDetails.getTotal());
                    Compra updatedCompra = compraRepository.save(compra);
                    return ResponseEntity.ok(updatedCompra);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/compras/{id} - Cancelar compra
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarCompra(@PathVariable Integer id) {
        return compraRepository.findById(id)
                .map(compra -> {
                    compraRepository.delete(compra);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/compras/proveedor/{proveedorId} - Compras por proveedor
    @GetMapping("/proveedor/{proveedorId}")
    public List<Compra> comprasPorProveedor(@PathVariable Integer proveedorId) {
        return compraRepository.findByProveedorId(proveedorId);
    }

    // GET /api/compras/fecha/{fecha} - Compras por fecha
    @GetMapping("/fecha/{fecha}")
    public List<Compra> comprasPorFecha(@PathVariable String fecha) {
        LocalDate date = LocalDate.parse(fecha);
        return compraRepository.findByFecha(date);
    }

    // GET /api/compras/usuario/{usuarioId} - Compras por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Compra> comprasPorUsuario(@PathVariable Integer usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }
}
