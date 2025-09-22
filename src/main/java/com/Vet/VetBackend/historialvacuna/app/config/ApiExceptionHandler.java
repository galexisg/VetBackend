package com.Vet.VetBackend.historialvacuna.app.config; // Correct package declaration

import com.Vet.VetBackend.historialvacuna.app.web.exception.BadRequestException; // Ensure this import is correct
import com.Vet.VetBackend.historialvacuna.app.web.exception.NotFoundException; // Ensure this import is correct

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> bad(BadRequestException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
    }

    // Additional exception handlers can be added here
}