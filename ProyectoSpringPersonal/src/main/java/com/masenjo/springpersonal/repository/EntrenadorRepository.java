package com.masenjo.springpersonal.repository;

import com.masenjo.springpersonal.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    Entrenador findByNombre(String nombre);
}
