package com.miapp.escuela.service;

import com.miapp.escuela.model.Trabajo;

import java.util.List;
import java.util.Optional;

public interface TrabajoService {
    List<Trabajo> obtenerTodosLosTrabajos();
    Optional<Trabajo> obtenerTrabajoPorId(Long id);
    Trabajo guardarTrabajo(Trabajo trabajo);
    void eliminarTrabajo(Long id);
    List<Trabajo> buscarPorTitulo(String titulo);
}
