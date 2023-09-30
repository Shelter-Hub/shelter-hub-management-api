package com.shelterhub.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(
            InvalidFormatException ex
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid request parameter: " +
                        ex.getPath().get(0).getFieldName()
        );
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidValueException(InvalidValueException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid value provided for the enum: " +
                        ex.getLocalizedMessage()
        );
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }


    @ExceptionHandler(PersistenceFailedException.class)
    public ResponseEntity<ErrorResponse> handlePersistenceFailedException(PersistenceFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Error while trying to persist a new animal: " +
                        ex.getLocalizedMessage()
        );
        return ResponseEntity
                .internalServerError()
                .body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Some constraint in the request was not properly validated: " +
                        ex.getLocalizedMessage()
        );
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException() {
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(NotUpdatableException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            NotUpdatableException ex
    ) {
        ErrorResponse errorResponse =
                new ErrorResponse(ex.getLocalizedMessage());

        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
}
