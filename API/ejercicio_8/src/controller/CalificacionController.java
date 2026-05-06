package com.et20.escolar.controller;

import com.et20.escolar.model.Calificacion;
import com.et20.escolar.service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/calificaciones")
@Tag(name = "Calificaciones", description = "Gestión de calificaciones por estudiante y materia")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class CalificacionController {

    @Autowired
    private CalificacionService service;

    // GET /api/calificaciones
    @GetMapping
    @Operation(summary = "Listar todas las calificaciones")
    public ResponseEntity<List<Calificacion>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // GET /api/calificaciones/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Buscar calificación por ID")
    public ResponseEntity<Calificacion> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // GET /api/calificaciones/estudiante/{id}
    @GetMapping("/estudiante/{estudianteId}")
    @Operation(summary = "Obtener todas las calificaciones de un estudiante")
    public ResponseEntity<List<Calificacion>> porEstudiante(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(service.porEstudiante(estudianteId));
    }

    // GET /api/calificaciones/materia/{id}
    @GetMapping("/materia/{materiaId}")
    @Operation(summary = "Obtener todas las calificaciones de una materia")
    public ResponseEntity<List<Calificacion>> porMateria(
            @PathVariable Long materiaId) {
        return ResponseEntity.ok(service.porMateria(materiaId));
    }

    // GET /api/calificaciones/promedio?estudianteId=1&materiaId=2
    @GetMapping("/promedio")
    @Operation(summary = "Calcular promedio de un estudiante en una materia")
    public ResponseEntity<Map<String, Object>> promedio(
            @RequestParam Long estudianteId,
            @RequestParam Long materiaId) {
        Double prom = service.promedio(estudianteId, materiaId);
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("estudianteId", estudianteId);
        res.put("materiaId", materiaId);
        res.put("promedio", prom != null
            ? Math.round(prom * 100.0) / 100.0
            : "Sin calificaciones");
        return ResponseEntity.ok(res);
    }

    // POST /api/calificaciones
    @PostMapping
    @Operation(summary = "Registrar nueva calificación",
               description = "Body: { estudianteId, materiaId, nota, trimestre }")
    public ResponseEntity<Calificacion> crear(@RequestBody Map<String, Object> body) {
        Long estudianteId = Long.valueOf(body.get("estudianteId").toString());
        Long materiaId    = Long.valueOf(body.get("materiaId").toString());
        Double nota       = Double.valueOf(body.get("nota").toString());
        String trimestre  = body.get("trimestre").toString();

        Calificacion c = service.crear(estudianteId, materiaId, nota, trimestre);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    // DELETE /api/calificaciones/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una calificación")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}