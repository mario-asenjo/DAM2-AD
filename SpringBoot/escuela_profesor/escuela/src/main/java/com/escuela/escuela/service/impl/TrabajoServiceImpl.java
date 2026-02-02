package com.escuela.escuela.service.impl;

import com.escuela.escuela.model.Trabajo;
import com.escuela.escuela.repository.TrabajoRepository;
import com.escuela.escuela.service.TrabajoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajoServiceImpl implements TrabajoService {

    private final TrabajoRepository trabajoRepository;

    // Inyección de dependencias a través del constructor
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
        // Aquí puedes añadir validaciones adicionales antes de guardar
        return trabajoRepository.save(trabajo);
    }

    @Override
    public void eliminarTrabajo(Long id) {
        trabajoRepository.deleteById(id);
    }

    @Override
    public List<Trabajo> buscarPorTitulo(String titulo) {
        // Buscar trabajos cuyo título contenga el texto indicado (consulta personalizada)
        return trabajoRepository.findByTituloContaining(titulo);
    }
}
