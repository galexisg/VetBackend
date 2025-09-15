package com.Vet.VetBackend.facturacion.web.controller;

import com.Vet.VetBackend.facturacion.app.PagoService;
import com.Vet.VetBackend.facturacion.web.dto.PagoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // ========== OPERACIONES CRUD ==========

    /**
     * Registrar nuevo pago
     * POST /api/pagos
     */
    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody PagoRequest request) {
        try {
            PagoDTO nuevoPago = pagoService.registrarPago(
                    request.getFacturaId(),
                    request.getMetodo(),
                    request.getMonto(),
                    request.getFechaPago()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * Obtener pago por ID
     * GET /api/pagos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPago(@PathVariable Long id) {
        try {
            Optional<PagoDTO> pago = pagoService.obtenerPagoPorId(id);
            if (pago.isPresent()) {
                return ResponseEntity.ok(pago.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener el pago"));
        }
    }

    /**
     * Obtener pagos por factura
     * GET /api/pagos/factura/{facturaId}
     */
    @GetMapping("/factura/{facturaId}")
    public ResponseEntity<?> obtenerPagosPorFactura(@PathVariable Long facturaId) {
        try {
            List<PagoDTO> pagos = pagoService.obtenerPagosPorFactura(facturaId);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener pagos de la factura"));
        }
    }

    /**
     * Obtener pagos por método
     * GET /api/pagos/metodo/{metodo}
     */
    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<?> obtenerPagosPorMetodo(@PathVariable String metodo) {
        try {
            List<PagoDTO> pagos = pagoService.obtenerPagosPorMetodo(metodo);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener pagos por método"));
        }
    }

    /**
     * Obtener pagos en período
     * GET /api/pagos/periodo?fechaInicio=&fechaFin=
     */
    @GetMapping("/periodo")
    public ResponseEntity<?> obtenerPagosEnPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<PagoDTO> pagos = pagoService.obtenerPagosEnPeriodo(fechaInicio, fechaFin);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener pagos en período"));
        }
    }

    /**
     * Obtener pagos de un cliente
     * GET /api/pagos/cliente/{clienteId}
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> obtenerPagosPorCliente(@PathVariable Long clienteId) {
        try {
            List<PagoDTO> pagos = pagoService.obtenerPagosPorCliente(clienteId);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener pagos del cliente"));
        }
    }

    /**
     * Anular pago
     * DELETE /api/pagos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> anularPago(@PathVariable Long id,
                                        @RequestBody(required = false) AnulacionRequest request) {
        try {
            String motivo = request != null ? request.getMotivo() : "Sin motivo especificado";
            pagoService.anularPago(id, motivo);
            return ResponseEntity.ok(new SuccessResponse("Pago anulado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al anular pago"));
        }
    }

    // ========== CONSULTAS Y VALIDACIONES ==========

    /**
     * Obtener total pagado por factura
     * GET /api/pagos/factura/{facturaId}/total
     */
    @GetMapping("/factura/{facturaId}/total")
    public ResponseEntity<?> obtenerTotalPagado(@PathVariable Long facturaId) {
        try {
            BigDecimal totalPagado = pagoService.obtenerTotalPagado(facturaId);
            return ResponseEntity.ok(new TotalResponse(totalPagado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener total pagado"));
        }
    }

    /**
     * Validar si se puede registrar un pago
     * GET /api/pagos/validar?facturaId=&monto=
     */
    @GetMapping("/validar")
    public ResponseEntity<?> validarPago(@RequestParam Long facturaId,
                                         @RequestParam BigDecimal monto) {
        try {
            boolean puedeRegistrar = pagoService.puedeRegistrarPago(facturaId, monto);
            BigDecimal saldoDespues = pagoService.calcularSaldoDespuesPago(facturaId, monto);

            ValidacionResponse response = new ValidacionResponse(puedeRegistrar, saldoDespues);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al validar pago"));
        }
    }

    /**
     * Obtener último pago de factura
     * GET /api/pagos/factura/{facturaId}/ultimo
     */
    @GetMapping("/factura/{facturaId}/ultimo")
    public ResponseEntity<?> obtenerUltimoPago(@PathVariable Long facturaId) {
        try {
            Optional<PagoDTO> ultimoPago = pagoService.obtenerUltimoPago(facturaId);
            if (ultimoPago.isPresent()) {
                return ResponseEntity.ok(ultimoPago.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener último pago"));
        }
    }

    // ========== ESTADÍSTICAS Y REPORTES ==========

    /**
     * Obtener recaudación por método
     * GET /api/pagos/estadisticas/metodos
     */
    @GetMapping("/estadisticas/metodos")
    public ResponseEntity<?> obtenerRecaudacionPorMetodo() {
        try {
            List<PagoService.RecaudacionPorMetodo> recaudacion =
                    pagoService.obtenerRecaudacionPorMetodo();
            return ResponseEntity.ok(recaudacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener estadísticas por método"));
        }
    }

    /**
     * Obtener recaudación diaria
     * GET /api/pagos/estadisticas/diaria?fechaInicio=&fechaFin=
     */
    @GetMapping("/estadisticas/diaria")
    public ResponseEntity<?> obtenerRecaudacionDiaria(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<PagoService.RecaudacionDiaria> recaudacion =
                    pagoService.obtenerRecaudacionDiaria(fechaInicio, fechaFin);
            return ResponseEntity.ok(recaudacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener recaudación diaria"));
        }
    }

    /**
     * Obtener total recaudado en período
     * GET /api/pagos/estadisticas/total?fechaInicio=&fechaFin=
     */
    @GetMapping("/estadisticas/total")
    public ResponseEntity<?> obtenerTotalRecaudado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            BigDecimal totalRecaudado = pagoService.obtenerTotalRecaudado(fechaInicio, fechaFin);
            return ResponseEntity.ok(new TotalResponse(totalRecaudado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener total recaudado"));
        }
    }

    /**
     * Obtener estadísticas de hoy
     * GET /api/pagos/estadisticas/hoy
     */
    @GetMapping("/estadisticas/hoy")
    public ResponseEntity<?> obtenerEstadisticasHoy() {
        try {
            PagoService.EstadisticasPagosHoy estadisticas =
                    pagoService.obtenerEstadisticasHoy();
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener estadísticas de hoy"));
        }
    }

    // ========== CLASES INTERNAS PARA REQUESTS Y RESPONSES ==========

    public static class PagoRequest {
        private Long facturaId;
        private String metodo;
        private BigDecimal monto;
        private LocalDateTime fechaPago;

        public PagoRequest() {}

        public Long getFacturaId() { return facturaId; }
        public void setFacturaId(Long facturaId) { this.facturaId = facturaId; }

        public String getMetodo() { return metodo; }
        public void setMetodo(String metodo) { this.metodo = metodo; }

        public BigDecimal getMonto() { return monto; }
        public void setMonto(BigDecimal monto) { this.monto = monto; }

        public LocalDateTime getFechaPago() { return fechaPago; }
        public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    }

    public static class AnulacionRequest {
        private String motivo;

        public AnulacionRequest() {}

        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }

    public static class TotalResponse {
        private BigDecimal total;

        public TotalResponse(BigDecimal total) { this.total = total; }

        public BigDecimal getTotal() { return total; }
    }

    public static class ValidacionResponse {
        private boolean puedeRegistrar;
        private BigDecimal saldoDespuesPago;

        public ValidacionResponse(boolean puedeRegistrar, BigDecimal saldoDespuesPago) {
            this.puedeRegistrar = puedeRegistrar;
            this.saldoDespuesPago = saldoDespuesPago;
        }

        public boolean isPuedeRegistrar() { return puedeRegistrar; }
        public BigDecimal getSaldoDespuesPago() { return saldoDespuesPago; }
    }

    public static class SuccessResponse {
        private String mensaje;
        private LocalDateTime timestamp;

        public SuccessResponse(String mensaje) {
            this.mensaje = mensaje;
            this.timestamp = LocalDateTime.now();
        }

        public String getMensaje() { return mensaje; }
        public LocalDateTime getTimestamp() { return timestamp; }
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