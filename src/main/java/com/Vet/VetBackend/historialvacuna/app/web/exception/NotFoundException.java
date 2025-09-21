package com.Vet.VetBackend.historialvacuna.app.web.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);  // Call the RuntimeException constructor with the message
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);  // For handling exceptions with cause
    }
}