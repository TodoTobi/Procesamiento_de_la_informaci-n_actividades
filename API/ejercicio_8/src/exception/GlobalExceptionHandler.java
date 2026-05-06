package com.et20.escolar.exception;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(RecursoNotFoundException ex) {
        return error(404, "Recurso no encontrado", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacion(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors())
            campos.put(fe.getField(), fe.getDefaultMessage());

        Map<String, Object> body = buildBody(400, "Error de validación");
        body.put("campos", campos);
        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> general(Exception ex) {
        return error(500, "Error interno", ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> error(int status, String tipo, String msg) {
        Map<String, Object> body = buildBody(status, tipo);
        body.put("mensaje", msg);
        return ResponseEntity.status(status).body(body);
    }

    private Map<String, Object> buildBody(int status, String tipo) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", tipo);
        return body;
    }
}