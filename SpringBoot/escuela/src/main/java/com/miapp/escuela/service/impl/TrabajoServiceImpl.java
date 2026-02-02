package com.miapp.escuela.service.impl;

import com.miapp.escuela.model.Trabajo;
import com.miapp.escuela.repository.TrabajoRepository;
import com.miapp.escuela.service.TrabajoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajoServiceImpl implements TrabajoService {
    private final TrabajoRepository trabajoRepository;

    public TrabajoServiceImpl(TrabajoRepository trabajoRepository) {
        this.trabajoRepository = trabajoRepository;
    }

    @Override
    public List<Trabajo> obtenerTodosLosTrabajos() {
        return trabajoRepository.findAll();
    }

    @Override
    public Optional<Trabajo> obtenerTrabajoPorId(Long id) {
        return trabajoRepository.findById(id);
    }

    @Override
    public Trabajo guardarTrabajo(Trabajo trabajo) {
        return trabajoRepository.save(trabajo);
    }

    @Override
    public void eliminarTrabajo(Long id) {
        trabajoRepository.deleteById(id);
    }

    @Override
    public List<Trabajo> buscarPorTitulo(String titulo) {
        return trabajoRepository.findByTituloContaining(titulo);
    }
}
