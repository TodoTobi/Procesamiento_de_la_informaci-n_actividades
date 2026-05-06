// Ejercicio 3

package com.et20.alumnos.exception;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Error cuando no se encuentra un alumno (404)
    @ExceptionHandler(AlumnoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(AlumnoNotFoundException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", 404);
        error.put("error", "Recurso no encontrado");
        error.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Error de validación: campos vacíos, formato incorrecto, etc. (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", 400);
        error.put("error", "Error de validación");

        // Recopila todos los errores de campo
        Map<String, String> erroresCampos = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            erroresCampos.put(fe.getField(), fe.getDefaultMessage());
        }
        error.put("campos", erroresCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Cualquier otro error inesperado (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarGeneral(Exception ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", 500);
        error.put("error", "Error interno del servidor");
        error.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
