//Ejercicio 2 + 3

package com.et20.alumnos.service;

import com.et20.alumnos.exception.AlumnoNotFoundException;
import com.et20.alumnos.model.Alumno;
import com.et20.alumnos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository repository;

    // Listar todos
    public List<Alumno> listarTodos() {
        return repository.findAll();
    }

    // Buscar por ID (lanza excepción si no existe – Ejercicio 3)
    public Alumno buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new AlumnoNotFoundException(
                "No se encontró ningún alumno con ID: " + id
            ));
    }

    // Crear nuevo alumno
    public Alumno crear(Alumno alumno) {
        return repository.save(alumno);
    }

    // Editar alumno existente (Ejercicio 2 + 3)
    public Alumno editar(Long id, Alumno datosNuevos) {
        Alumno existente = buscarPorId(id); // lanza excepción si no existe
        existente.setNombre(datosNuevos.getNombre());
        existente.setDni(datosNuevos.getDni());
        existente.setAnio(datosNuevos.getAnio());
        existente.setEmail(datosNuevos.getEmail());
        return repository.save(existente);
    }

    // Eliminar alumno
    public void eliminar(Long id) {
        buscarPorId(id); // valida que exista antes de eliminar
        repository.deleteById(id);
    }
}
