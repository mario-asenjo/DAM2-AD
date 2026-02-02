package com.escuela.escuela.repository;

import com.escuela.escuela.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para la entidad Alumno
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    // Aquí puedes definir métodos personalizados si los necesitas
    Alumno findByNombre(String nombre); // Ejemplo: buscar un alumno por su nombre
}
