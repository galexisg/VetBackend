package com.Vet.VetBackend.facturacion.web.controller;

import com.Vet.VetBackend.facturacion.app.FacturaService;
import com.Vet.VetBackend.facturacion.app.FacturaDetalleService;
import com.Vet.VetBackend.facturacion.web.dto.FacturaDTO;
import com.Vet.VetBackend.facturacion.web.dto.FacturaRequestDTO;
import com.Vet.VetBackend.facturacion.web.dto.FacturaDetalleDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaDetalleService facturaDetalleService;

    // ========== OPERACIONES CRUD ==========


    /**
     * Obtener todas las facturas
     * GET /api/facturas
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodasFacturas() {
        try {
            List<FacturaDTO> facturas = facturaService.obtenerTodasFacturas();
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener todas las facturas"));
        }
    }
    /**
     * Crear nueva factura
     * POST /api/facturas
     */
    @PostMapping
    public ResponseEntity<?> crearFactura(@RequestBody FacturaRequestDTO request) {
        try {
            FacturaDTO nuevaFactura = facturaService.crearFactura(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * Obtener factura por ID
     * GET /api/facturas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFactura(@PathVariable Long id) {
        try {
            Optional<FacturaDTO> factura = facturaService.obtenerFacturaPorId(id);
            if (factura.isPresent()) {
                return ResponseEntity.ok(factura.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener la factura"));
        }
    }

    /**
     * Obtener facturas por cliente
     * GET /api/facturas/cliente/{clienteId}
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> obtenerFacturasPorCliente(@PathVariable Long clienteId) {
        try {
            List<FacturaDTO> facturas = facturaService.obtenerFacturasPorCliente(clienteId);
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener facturas del cliente"));
        }
    }

    /**
     * Obtener facturas por estado
     * GET /api/facturas/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerFacturasPorEstado(@PathVariable String estado) {
        try {
            List<FacturaDTO> facturas = facturaService.obtenerFacturasPorEstado(estado);
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener facturas por estado"));
        }
    }

    /**
     * Obtener facturas pendientes
     * GET /api/facturas/pendientes
     */
    @GetMapping("/pendientes")
    public ResponseEntity<?> obtenerFacturasPendientes() {
        try {
            List<FacturaDTO> facturas = facturaService.obtenerFacturasPendientes();
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener facturas pendientes"));
        }
    }

    // ========== OPERACIONES DE ESTADO ==========

    /**
     * Actualizar estado de factura
     * PUT /api/facturas/{id}/estado
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id,
                                              @RequestBody EstadoRequest request) {
        try {
            FacturaDTO facturaActualizada = facturaService.actualizarEstado(id, request.getNuevoEstado());
            return ResponseEntity.ok(facturaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al actualizar estado de factura"));
        }
    }

    /**
     * Anular factura
     * PUT /api/facturas/{id}/anular
     */
    @PutMapping("/{id}/anular")
    public ResponseEntity<?> anularFactura(@PathVariable Long id,
                                           @RequestBody AnulacionRequest request) {
        try {
            FacturaDTO facturaAnulada = facturaService.anularFactura(
                    id, request.getMotivo(), request.getUsuarioId());
            return ResponseEntity.ok(facturaAnulada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al anular factura"));
        }
    }

    /**
     * Recalcular totales de factura
     * PUT /api/facturas/{id}/recalcular
     */
    @PutMapping("/{id}/recalcular")
    public ResponseEntity<?> recalcularTotales(@PathVariable Long id) {
        try {
            FacturaDTO facturaRecalculada = facturaService.recalcularTotales(id);
            return ResponseEntity.ok(facturaRecalculada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al recalcular totales"));
        }
    }

    // ========== OPERACIONES DE DETALLES ==========

    /**
     * Obtener detalles de factura
     * GET /api/facturas/{id}/detalles
     */
    @GetMapping("/{id}/detalles")
    public ResponseEntity<?> obtenerDetallesFactura(@PathVariable Long id) {
        try {
            List<FacturaDetalleDTO> detalles = facturaDetalleService.obtenerDetallesPorFactura(id);
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener detalles de factura"));
        }
    }

    /**
     * Agregar detalle a factura
     * POST /api/facturas/{id}/detalles
     */
    @PostMapping("/{id}/detalles")
    public ResponseEntity<?> agregarDetalle(@PathVariable Long id,
                                            @RequestBody DetalleRequest request) {
        try {
            FacturaDetalleDTO nuevoDetalle = facturaDetalleService.agregarDetalle(
                    id, request.getServicioId(), request.getCantidad(),
                    request.getPrecioUnitario(), request.getDescuento());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al agregar detalle"));
        }
    }

    // ========== REPORTES Y ESTADÍSTICAS ==========

    /**
     * Obtener resumen financiero
     * GET /api/facturas/resumen?fechaInicio=&fechaFin=
     */
    @GetMapping("/resumen")
    public ResponseEntity<?> obtenerResumenFinanciero(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            FacturaService.ResumenFinanciero resumen =
                    facturaService.obtenerResumenFinanciero(fechaInicio, fechaFin);
            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener resumen financiero"));
        }
    }

    // ========== CLASES INTERNAS PARA REQUESTS ==========

    public static class EstadoRequest {
        private String nuevoEstado;

        public EstadoRequest() {}

        public String getNuevoEstado() { return nuevoEstado; }
        public void setNuevoEstado(String nuevoEstado) { this.nuevoEstado = nuevoEstado; }
    }

    public static class AnulacionRequest {
        private String motivo;
        private Long usuarioId;

        public AnulacionRequest() {}

        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }

        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    }

    public static class DetalleRequest {
        private Long servicioId;
        private Integer cantidad;
        private java.math.BigDecimal precioUnitario;
        private java.math.BigDecimal descuento;

        public DetalleRequest() {}

        public Long getServicioId() { return servicioId; }
        public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

        public java.math.BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(java.math.BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

        public java.math.BigDecimal getDescuento() { return descuento; }
        public void setDescuento(java.math.BigDecimal descuento) { this.descuento = descuento; }
    }

    public static class ErrorResponse {
        private String mensaje;
        private LocalDateTime timestamp;

        public ErrorResponse(String mensaje) {
            this.mensaje = mensaje;
            this.timestamp = LocalDateTime.now();
        }

        public String getMensaje() { return mensaje; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}