package com.shelterhub.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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
                "Invalid filling when trying to persist a new animal: " +
                        ex.getLocalizedMessage()
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
