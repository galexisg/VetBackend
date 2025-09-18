package com.Vet.VetBackend.vacuna.app.config;

import com.Vet.VetBackend.vacuna.web.exception.BadRequestException;
import com.Vet.VetBackend.vacuna.web.exception.NotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> bad(BadRequestException e){
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
