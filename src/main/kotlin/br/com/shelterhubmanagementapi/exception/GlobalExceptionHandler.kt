package br.com.shelterhubmanagementapi.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormatException(ex: InvalidFormatException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Invalid request parameter: ${ex.localizedMessage}")
        return ResponseEntity
            .badRequest()
            .body(errorResponse)
    }

    @ExceptionHandler(InvalidValueException::class)
    fun handleInvalidValueException(ex: InvalidValueException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Invalid value provided for the enum: ${ex.localizedMessage}")
        return ResponseEntity
            .badRequest()
            .body(errorResponse)
    }

    @ExceptionHandler(PersistenceFailedException::class)
    fun handlePersistenceFailedException(ex: PersistenceFailedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Error while trying to persist a new animal: ${ex.localizedMessage}")
        return ResponseEntity
            .internalServerError()
            .body(errorResponse)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .notFound()
            .build()
    }

    @ExceptionHandler(NotUpdatableException::class)
    fun handleConstraintViolationException(ex: NotUpdatableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.localizedMessage)
        return ResponseEntity
            .badRequest()
            .body(errorResponse)
    }

    @ExceptionHandler(UnknownErrorException::class)
    fun handleInternalServerError(ex: UnknownErrorException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Some unknown problem happened: ${ex.localizedMessage}")
        return ResponseEntity
            .internalServerError()
            .body(errorResponse)
    }
}
