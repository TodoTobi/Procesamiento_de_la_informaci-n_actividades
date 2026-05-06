package com.et20.escolar.service;

import com.et20.escolar.exception.RecursoNotFoundException;
import com.et20.escolar.model.Materia;
import com.et20.escolar.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository repo;

    public List<Materia> listarTodas() { return repo.findAll(); }

    public Materia buscarPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RecursoNotFoundException(
                "Materia no encontrada con ID: " + id));
    }

    public Materia crear(Materia m) { return repo.save(m); }

    public Materia editar(Long id, Materia datos) {
        Materia existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setDocente(datos.getDocente());
        existente.setAnio(datos.getAnio());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        repo.deleteById(id);
    }
}