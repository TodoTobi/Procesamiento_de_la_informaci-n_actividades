//Ejercicio 2

package com.et20.alumnos.repository;

import com.et20.alumnos.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    // Buscar por DNI
    Optional<Alumno> findByDni(String dni);

    // Buscar por año
    List<Alumno> findByAnio(Integer anio);

    // Buscar por nombre (sin distinguir mayúsculas)
    List<Alumno> findByNombreContainingIgnoreCase(String nombre);
}
