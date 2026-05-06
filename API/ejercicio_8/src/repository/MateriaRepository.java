package com.et20.escolar.repository;

import com.et20.escolar.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByAnio(Integer anio);
    List<Materia> findByNombreContainingIgnoreCase(String nombre);
}