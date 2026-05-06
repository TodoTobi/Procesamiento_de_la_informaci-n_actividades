package com.et20.escolar.service;

import com.et20.escolar.exception.RecursoNotFoundException;
import com.et20.escolar.model.Estudiante;
import com.et20.escolar.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository repo;

    public List<Estudiante> listarTodos() { return repo.findAll(); }

    public Estudiante buscarPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RecursoNotFoundException(
                "Estudiante no encontrado con ID: " + id));
    }

    public Estudiante crear(Estudiante e) { return repo.save(e); }

    public Estudiante editar(Long id, Estudiante datos) {
        Estudiante existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setDni(datos.getDni());
        existente.setAnio(datos.getAnio());
        existente.setEmail(datos.getEmail());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        repo.deleteById(id);
    }
}