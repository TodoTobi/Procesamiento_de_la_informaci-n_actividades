package com.et20.escolar.service;

import com.et20.escolar.exception.RecursoNotFoundException;
import com.et20.escolar.model.*;
import com.et20.escolar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CalificacionService {

    @Autowired private CalificacionRepository repo;
    @Autowired private EstudianteRepository estudianteRepo;
    @Autowired private MateriaRepository materiaRepo;

    public List<Calificacion> listarTodas() { return repo.findAll(); }

    public Calificacion buscarPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RecursoNotFoundException(
                "Calificación no encontrada con ID: " + id));
    }

    public List<Calificacion> porEstudiante(Long estudianteId) {
        return repo.findByEstudianteId(estudianteId);
    }

    public List<Calificacion> porMateria(Long materiaId) {
        return repo.findByMateriaId(materiaId);
    }

    public Double promedio(Long estudianteId, Long materiaId) {
        return repo.promedioEstudianteMateria(estudianteId, materiaId);
    }

    public Calificacion crear(Long estudianteId, Long materiaId,
                              Double nota, String trimestre) {
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
            .orElseThrow(() -> new RecursoNotFoundException(
                "Estudiante no encontrado con ID: " + estudianteId));
        Materia materia = materiaRepo.findById(materiaId)
            .orElseThrow(() -> new RecursoNotFoundException(
                "Materia no encontrada con ID: " + materiaId));

        Calificacion c = new Calificacion();
        c.setEstudiante(estudiante);
        c.setMateria(materia);
        c.setNota(nota);
        c.setTrimestre(trimestre);
        return repo.save(c);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        repo.deleteById(id);
    }
}   