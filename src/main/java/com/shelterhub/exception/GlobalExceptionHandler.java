package com.shelterhub.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid request parameter: " +
                ex.getPath().get(0).getFieldName()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(PersistenceFailedException.class)
    public ResponseEntity<ErrorResponse> handlePersistenceFailedException(PersistenceFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Error while trying to persist a new animal: " +
                        ex.getLocalizedMessage()
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlePContraintViolationException(ConstraintViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Some constraint in the request was not validated well: " +
                        ex.getLocalizedMessage()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePContraintViolationException() {
        return ResponseEntity.notFound().build();
    }

}
