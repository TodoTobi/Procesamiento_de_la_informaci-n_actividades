// Ejercicio 2

package com.et20.alumnos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "alumnos")
@Schema(description = "Representa un alumno del establecimiento educativo")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico autogenerado", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre completo del alumno", example = "Juan Perez")
    private String nombre;

    @NotBlank(message = "El DNI no puede estar vacio")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener 7 u 8 digitos")
    @Column(unique = true)
    @Schema(description = "DNI del alumno (7 u 8 digitos)", example = "44123456")
    private String dni;

    @Min(value = 1, message = "El anio debe ser entre 1 y 7")
    @Max(value = 7, message = "El anio debe ser entre 1 y 7")
    @Schema(description = "Anio que cursa el alumno (1 a 7)", example = "3")
    private Integer anio;

    @Email(message = "El email no tiene un formato valido")
    @Schema(description = "Correo electronico del alumno", example = "juan.perez@ejemplo.com")
    private String email;

    public Alumno() {
    }

    public Alumno(Long id, String nombre, String dni, Integer anio, String email) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.anio = anio;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
