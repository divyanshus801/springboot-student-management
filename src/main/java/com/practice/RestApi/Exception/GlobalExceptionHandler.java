package com.practice.RestApi.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ===================== 1. BUSINESS ERRORS ===================== */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");
        body.put("message", ex.getReason()); // <-- shows your actual message

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(body);
    }

    /* ===================== 2. VALIDATION ERRORS ===================== */
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

    /* ===================== 1. DATABASE CONSTRAINT ERRORS ===================== */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleUniqueConstraintError(DataIntegrityViolationException ex) {

        String message = "Invalid Data";

        if(ex.getCause() != null && ex.getCause().getMessage().contains("duplicate")){
            message = "Duplicate entry found, violates unique constraint.";
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");
        body.put("message", message); // <-- shows your actual message

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }

    /* ===================== 4. REQUEST BODY VALIDATION ERRORS ================= */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(HttpMessageNotReadableException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");

        String message = "Invalid Request Body";

        Throwable rootCause = ex.getMostSpecificCause();

        if (rootCause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife) {
            if (ife.getTargetType().equals(LocalDateTime.class)) {
                message = "Invalid date-time format. Use yyyy-MM-dd'T'HH:mm:ss";
            } else {
                message = "Invalid value for field: " + ife.getPathReference();
            }
        }
         body.put("message", message);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
