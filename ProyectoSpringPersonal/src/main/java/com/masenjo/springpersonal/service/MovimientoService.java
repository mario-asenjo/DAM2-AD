package com.masenjo.springpersonal.service;

import com.masenjo.springpersonal.model.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoService {
    List<Movimiento> obtenerTodos();
    Optional<Movimiento> obtenerPorId(Long id);
    Movimiento guardar(Movimiento movimiento);
    void eliminar(Long id);
}
