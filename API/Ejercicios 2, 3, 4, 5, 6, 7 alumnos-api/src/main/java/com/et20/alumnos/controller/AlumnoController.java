// Ejercicio 2 + Ejercicio 6 – Swagger

package com.et20.alumnos.controller;

import com.et20.alumnos.model.Alumno;
import com.et20.alumnos.service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@Tag(name = "Alumnos", description = "Operaciones CRUD sobre el recurso Alumno")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*") // Permite consumo desde el frontend web (Ejercicio 5)
public class AlumnoController {

    @Autowired
    private AlumnoService service;

    // GET /api/alumnos
    @GetMapping
    @Operation(
        summary = "Listar todos los alumnos",
        description = "Devuelve la lista completa de alumnos registrados en el sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
        }
    )
    public ResponseEntity<List<Alumno>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET /api/alumnos/{id}
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar alumno por ID",
        description = "Devuelve un alumno específico dado su ID.",
        parameters = @Parameter(name = "id", description = "ID del alumno", example = "1"),
        responses = {
            @ApiResponse(responseCode = "200", description = "Alumno encontrado"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
                content = @Content(schema = @Schema(example = "{\"error\": \"No se encontró ningún alumno con ID: 99\"}")))
        }
    )
    public ResponseEntity<Alumno> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST /api/alumnos
    @PostMapping
    @Operation(
        summary = "Crear un nuevo alumno",
        description = "Registra un nuevo alumno en el sistema. El ID se genera automáticamente.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Alumno creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (nombre vacío, DNI incorrecto, etc.)")
        }
    )
    public ResponseEntity<Alumno> crear(@Valid @RequestBody Alumno alumno) {
        Alumno creado = service.crear(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/alumnos/{id}
    @PutMapping("/{id}")
    @Operation(
        summary = "Editar un alumno existente",
        description = "Reemplaza todos los datos de un alumno dado su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Alumno actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
        }
    )
    public ResponseEntity<Alumno> editar(
            @PathVariable Long id,
            @Valid @RequestBody Alumno alumno) {
        return ResponseEntity.ok(service.editar(id, alumno));
    }

    // DELETE /api/alumnos/{id}
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un alumno",
        description = "Borra definitivamente un alumno del sistema.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Alumno eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
        }
    )
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
