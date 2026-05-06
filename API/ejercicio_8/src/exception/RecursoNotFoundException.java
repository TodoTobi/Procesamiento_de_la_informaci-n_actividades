package com.et20.escolar.exception;

public class RecursoNotFoundException extends RuntimeException {
    public RecursoNotFoundException(String mensaje) {
        super(mensaje);
    }
}