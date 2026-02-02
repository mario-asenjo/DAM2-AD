package com.miapp.escuela.repository;

import com.miapp.escuela.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Alumno findByNombre(String nombre);
}
