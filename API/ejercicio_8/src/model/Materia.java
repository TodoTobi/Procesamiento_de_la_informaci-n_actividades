package com.et20.escolar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "materias")
@Schema(description = "Materia o asignatura dictada en el establecimiento")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la materia", example = "1")
    private Long id;

    @NotBlank(message = "El nombre de la materia no puede estar vacío")
    @Size(min = 2, max = 100)
    @Schema(description = "Nombre de la materia", example = "Matemáticas")
    private String nombre;

    @NotBlank(message = "El docente no puede estar vacío")
    @Schema(description = "Nombre del docente a cargo", example = "Prof. López")
    private String docente;

    @Min(1) @Max(7)
    @Schema(description = "Año en que se dicta (1 a 7)", example = "4")
    private Integer anio;

    public Materia() {
    }

    public Materia(Long id, String nombre, String docente, Integer anio) {
        this.id = id;
        this.nombre = nombre;
        this.docente = docente;
        this.anio = anio;
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

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
}
