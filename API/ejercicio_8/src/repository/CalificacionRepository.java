package com.et20.escolar.repository;

import com.et20.escolar.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    // Todas las calificaciones de un estudiante
    List<Calificacion> findByEstudianteId(Long estudianteId);

    // Todas las calificaciones de una materia
    List<Calificacion> findByMateriaId(Long materiaId);

    // Calificaciones de un estudiante en una materia específica
    List<Calificacion> findByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);

    // Promedio de notas de un estudiante en una materia
    @Query("SELECT AVG(c.nota) FROM Calificacion c " +
           "WHERE c.estudiante.id = :estudianteId AND c.materia.id = :materiaId")
    Double promedioEstudianteMateria(
        @Param("estudianteId") Long estudianteId,
        @Param("materiaId") Long materiaId
    );
}