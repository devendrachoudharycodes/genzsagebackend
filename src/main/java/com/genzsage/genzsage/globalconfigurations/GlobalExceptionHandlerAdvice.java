package com.genzsage.genzsage.globalconfigurations;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    /**
     * Handles Validation Errors (e.g., @NotBlank, @Email, @Size failures)
     * Thrown when @Valid fails on a Controller method argument.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDTO<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // 1. Extract all error messages from the binding result
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        // 2. Create the failure DTO
        GlobalResponseDTO<Object> response = GlobalResponseDTO.failure(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors
        );

        // 3. Return ResponseEntity with 400 Bad Request
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback Handler for any other unhandled exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponseDTO<Object>> handleGeneralExceptions(Exception ex) {

        // Create a generic error list
        List<String> errors = Collections.singletonList(ex.getMessage());

        GlobalResponseDTO<Object> response = GlobalResponseDTO.failure(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}