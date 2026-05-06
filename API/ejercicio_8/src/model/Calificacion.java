package com.et20.escolar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "calificaciones")
@Schema(description = "Calificación de un estudiante en una materia")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la calificación", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    @Schema(description = "Estudiante al que pertenece la calificación")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    @Schema(description = "Materia en la que se obtuvo la calificación")
    private Materia materia;

    @NotNull(message = "La nota no puede estar vacía")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1")
    @DecimalMax(value = "10.0", message = "La nota máxima es 10")
    @Schema(description = "Nota obtenida (1 a 10)", example = "8.5")
    private Double nota;

    @NotBlank(message = "El trimestre no puede estar vacío")
    @Schema(description = "Trimestre evaluado", example = "1° Trimestre",
            allowableValues = {"1° Trimestre", "2° Trimestre", "3° Trimestre", "Final"})
    private String trimestre;

    public Calificacion() {
    }

    public Calificacion(Long id, Estudiante estudiante, Materia materia, Double nota, String trimestre) {
        this.id = id;
        this.estudiante = estudiante;
        this.materia = materia;
        this.nota = nota;
        this.trimestre = trimestre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }
}
