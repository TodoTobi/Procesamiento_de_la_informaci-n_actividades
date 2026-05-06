package com.et20.escolar.controller;

import com.et20.escolar.model.Materia;
import com.et20.escolar.service.MateriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/materias")
@Tag(name = "Materias", description = "CRUD de materias")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class MateriaController {

    @Autowired
    private MateriaService service;

    @GetMapping
    @Operation(summary = "Listar todas las materias")
    public ResponseEntity<List<Materia>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar materia por ID")
    public ResponseEntity<Materia> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva materia")
    public ResponseEntity<Materia> crear(@Valid @RequestBody Materia m) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(m));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar materia existente")
    public ResponseEntity<Materia> editar(
            @PathVariable Long id, @Valid @RequestBody Materia m) {
        return ResponseEntity.ok(service.editar(id, m));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar materia")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}