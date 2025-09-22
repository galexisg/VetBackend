package com.Vet.VetBackend.historialvacuna.app.web.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);  // Call the RuntimeException constructor with the message
    }
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);  // For handling exceptions with cause
    }
}