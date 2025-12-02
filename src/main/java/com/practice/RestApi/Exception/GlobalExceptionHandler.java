package com.practice.RestApi.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");
        body.put("message", ex.getReason()); // <-- shows your actual message

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(body);
    }
}
