// src/main/java/com/Vet/VetBackend/servicios/web/controller/RestExceptionHandler.java
package com.Vet.VetBackend.servicios.web.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> notFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err("BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> conflict(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err("CONFLICT", "violación de integridad"));
    }

    private Map<String, Object> err(String code, String msg) {
        return Map.of("code", code, "message", msg, "timestamp", new Date());
    }
}
