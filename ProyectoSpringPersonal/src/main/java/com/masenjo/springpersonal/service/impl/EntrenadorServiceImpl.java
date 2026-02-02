package com.masenjo.springpersonal.service.impl;

import com.masenjo.springpersonal.model.Entrenador;
import com.masenjo.springpersonal.repository.EntrenadorRepository;
import com.masenjo.springpersonal.service.EntrenadorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorServiceImpl implements EntrenadorService {

    private final EntrenadorRepository repo;

    public EntrenadorServiceImpl(EntrenadorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Entrenador> obtenerTodos() { return repo.findAll(); }

    @Override
    public Optional<Entrenador> obtenerPorId(Long id) { return repo.findById(id); }

    @Override
    public Entrenador guardar(Entrenador entrenador) { return repo.save(entrenador); }

    @Override
    public void eliminar(Long id) { repo.deleteById(id); }
}
