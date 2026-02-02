package com.escuela.escuela.repository;

import com.escuela.escuela.model.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio para la entidad Trabajo
@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {
    // Ejemplo de método personalizado: buscar trabajos por su título
    List<Trabajo> findByTituloContaining(String titulo);
}
