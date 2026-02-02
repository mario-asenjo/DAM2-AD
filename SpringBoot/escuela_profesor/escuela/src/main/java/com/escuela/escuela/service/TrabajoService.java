package com.escuela.escuela.service;

import com.escuela.escuela.model.Trabajo;
import java.util.List;
import java.util.Optional;

// Definimos la interfaz del servicio para Trabajo
public interface TrabajoService {
    List<Trabajo> obtenerTodosLosTrabajos();
    Optional<Trabajo> obtenerTrabajoPorId(Long id);//Optional es una clase contenedora de Java que se usa para evitar errores de NullPointerException
    Trabajo guardarTrabajo(Trabajo trabajo);
    void eliminarTrabajo(Long id);
    List<Trabajo> buscarPorTitulo(String titulo);  // Método adicional para búsquedas personalizadas
}
