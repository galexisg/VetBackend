// src/main/java/com/Vet/VetBackend/servicios/web/controller/ServicioController.java
package com.Vet.VetBackend.servicios.web.controller;

import com.Vet.VetBackend.servicios.app.services.ServicioService;
import com.Vet.VetBackend.servicios.web.dto.ServicioReq;
import com.Vet.VetBackend.servicios.web.dto.ServicioRes;
import com.Vet.VetBackend.servicios.web.dto.ServicioEstadoReq;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService svc;
    public ServicioController(ServicioService svc) { this.svc = svc; }

    /**
     * Endpoint para crear un nuevo servicio.
     *
     * HTTP POST /api/servicios
     *
     * Body (JSON):
     * {
     * "nombre": "Consulta médica",
     * "descripcion": "Revisión general de la mascota.",
     * "precioBase": 50.00
     * }
     */
    @PostMapping
    public ResponseEntity<ServicioRes> crear(@Validated @RequestBody ServicioReq req) {
        return new ResponseEntity<>(svc.crear(req), HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar completamente un servicio existente.
     *
     * HTTP PUT /api/servicios/{id}
     *
     * Body (JSON):
     * {
     * "nombre": "Consulta médica actualizada",
     * "descripcion": "Revisión y diagnóstico avanzado.",
     * "precioBase": 60.00
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicioRes> actualizar(@PathVariable Long id, @Validated @RequestBody ServicioReq req) {
        return new ResponseEntity<>(svc.actualizar(id, req), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un servicio por su ID.
     *
     * HTTP GET /api/servicios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicioRes> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(svc.obtener(id), HttpStatus.OK);
    }

    /**
     * Endpoint para listar servicios con paginación y filtros opcionales.
     *
     * HTTP GET /api/servicios
     *
     * Query Parameters:
     * - q: string (ej. "vacuna")
     * - activo: boolean (ej. true para activos, false para inactivos)
     * - page: int (ej. 0)
     * - size: int (ej. 10)
     *
     * Ejemplo de uso:
     * - Obtener todos los servicios: GET /api/servicios
     * - Listar solo servicios activos en la página 2 con 5 elementos: GET /api/servicios?activo=true&page=1&size=5
     */
    @GetMapping
    public ResponseEntity<Page<ServicioRes>> listar(@RequestParam(required = false) String q,
                                                    @RequestParam(required = false) Boolean activo,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(svc.listar(q, activo, page, size), HttpStatus.OK);
    }

    /**
     * Endpoint para cambiar el estado de un servicio.
     *
     * HTTP PATCH /api/servicios/{id}
     *
     * Body (JSON):
     * {
     * "estado": "ACTIVO"
     * }
     *
     * Ejemplo de uso:
     * - Desactivar el servicio con ID 1: PATCH /api/servicios/1 con body {"estado": "INACTIVO"}
     * - Activar el servicio con ID 1: PATCH /api/servicios/1 con body {"estado": "ACTIVO"}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ServicioRes> cambiarEstado(
            @PathVariable Long id,
            @Validated @RequestBody ServicioEstadoReq req) {
        return new ResponseEntity<>(svc.cambiarEstado(id, req), HttpStatus.OK);
    }
}