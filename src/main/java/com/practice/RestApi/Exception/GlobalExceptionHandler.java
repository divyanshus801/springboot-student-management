package com.practice.RestApi.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {

        System.err.println(ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");
        body.put("message", ex.getReason()); // <-- shows your actual message

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });


        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "Validation Error");
        response.put("errors", errors );

        return ResponseEntity.badRequest().body(response);
    }

    public Void EmptyInputException() {
        return null;
    }
}
