package com.et20.escolar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "estudiantes")
@Schema(description = "Estudiante inscripto en el establecimiento")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100)
    @Schema(description = "Nombre completo", example = "María González")
    private String nombre;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener 7 u 8 dígitos")
    @Column(unique = true)
    @Schema(description = "DNI del estudiante", example = "44987654")
    private String dni;

    @Min(1) @Max(7)
    @Schema(description = "Año que cursa (1 a 7)", example = "4")
    private Integer anio;

    @Email(message = "El email no tiene formato válido")
    @Schema(description = "Correo electrónico", example = "maria@ejemplo.com")
    private String email;

    public Estudiante() {
    }

    public Estudiante(Long id, String nombre, String dni, Integer anio, String email) {
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
