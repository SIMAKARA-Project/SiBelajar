package org.simakara.learning_management_system.handler;

import jakarta.validation.ConstraintViolationException;
import org.simakara.learning_management_system.dto.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> handleResponseStatusException(
            ResponseStatusException exception
    ) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(
                        new WebResponse<>(
                                null,
                                exception.getStatusCode().value(),
                                exception.getStatusCode().toString(),
                                exception.getReason()
                        )
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new WebResponse<>(
                                null,
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                exception.getMessage()
                        )
                );
    }
}
