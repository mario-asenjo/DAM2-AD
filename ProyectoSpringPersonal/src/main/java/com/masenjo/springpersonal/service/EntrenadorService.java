package com.masenjo.springpersonal.service;

import com.masenjo.springpersonal.model.Entrenador;

import java.util.List;
import java.util.Optional;

public interface EntrenadorService {
    List<Entrenador> obtenerTodos();
    Optional<Entrenador> obtenerPorId(Long id);
    Entrenador guardar(Entrenador entrenador);
    void eliminar(Long id);
}
