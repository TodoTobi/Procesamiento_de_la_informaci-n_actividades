package com.et20.escolar.controller;

import com.et20.escolar.model.Estudiante;
import com.et20.escolar.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@Tag(name = "Estudiantes", description = "CRUD de estudiantes")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @GetMapping
    @Operation(summary = "Listar todos los estudiantes")
    public ResponseEntity<List<Estudiante>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estudiante por ID")
    public ResponseEntity<Estudiante> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo estudiante")
    public ResponseEntity<Estudiante> crear(@Valid @RequestBody Estudiante e) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(e));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar estudiante existente")
    public ResponseEntity<Estudiante> editar(
            @PathVariable Long id, @Valid @RequestBody Estudiante e) {
        return ResponseEntity.ok(service.editar(id, e));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estudiante")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}