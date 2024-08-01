package org.simakara.learning_management_system.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.simakara.learning_management_system.dto.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

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

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<WebResponse<String>> handleInvalidFormat(
            InvalidFormatException exception
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

    public ResponseEntity<WebResponse<String>> handleInvalidMediaType(
            InvalidMediaTypeException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(
                        new WebResponse<>(
                                null,
                                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                                HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(),
                                exception.getMessage()
                        )
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<WebResponse<String>> handleUsernameNotFound(
            UsernameNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new WebResponse<>(
                                null,
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                exception.getMessage()
                        )
                );
    }

    public ResponseEntity<WebResponse<String>> handleBadCredentials(
            BadCredentialsException exception
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

    @ExceptionHandler(IOException.class)
    public ResponseEntity<WebResponse<String>> handleIOException(
            UsernameNotFoundException exception
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

    public ResponseEntity<WebResponse<String>> handleException(
            Exception exception
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
