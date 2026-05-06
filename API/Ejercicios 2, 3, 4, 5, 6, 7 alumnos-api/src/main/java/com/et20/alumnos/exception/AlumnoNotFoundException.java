// Ejercicio 3

package com.et20.alumnos.exception;

public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
