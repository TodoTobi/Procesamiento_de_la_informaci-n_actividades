package com.et20.escolar.repository;

import com.et20.escolar.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByDni(String dni);
    List<Estudiante> findByAnio(Integer anio);
    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
}