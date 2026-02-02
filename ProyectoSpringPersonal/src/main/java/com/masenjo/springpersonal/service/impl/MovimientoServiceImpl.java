package com.masenjo.springpersonal.service.impl;

import com.masenjo.springpersonal.model.Movimiento;
import com.masenjo.springpersonal.repository.MovimientoRepository;
import com.masenjo.springpersonal.service.MovimientoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository repo;

    public MovimientoServiceImpl(MovimientoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Movimiento> obtenerTodos() { return repo.findAll(); }

    @Override
    public Optional<Movimiento> obtenerPorId(Long id) { return repo.findById(id); }

    @Override
    public Movimiento guardar(Movimiento movimiento) { return repo.save(movimiento); }

    @Override
    public void eliminar(Long id) { repo.deleteById(id); }
}
