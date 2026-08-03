package com.eduardo.condoops.exception.handler;

import com.eduardo.condoops.dto.error.StandardError;
import com.eduardo.condoops.exception.conflict.ResourceConflictException;
import com.eduardo.condoops.exception.notfound.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        StandardError standardError = buildStandardError(ex, request, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(standardError.status()).body(standardError);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<StandardError> handleResourceConflictException(
            ResourceConflictException ex,
            HttpServletRequest request
    ) {
        StandardError standardError = buildStandardError(ex, request, HttpStatus.CONFLICT);
        return ResponseEntity.status(standardError.status()).body(standardError);
    }


    private StandardError buildStandardError(
            Exception ex,
            HttpServletRequest request,
            HttpStatus httpStatus
    ) {

        return StandardError.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}
