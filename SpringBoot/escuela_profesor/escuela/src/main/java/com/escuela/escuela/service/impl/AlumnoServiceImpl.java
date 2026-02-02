package com.escuela.escuela.service.impl;

import com.escuela.escuela.model.Alumno;
import com.escuela.escuela.repository.AlumnoRepository;
import com.escuela.escuela.service.AlumnoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    // Inyección de dependencias a través del constructor
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoRepository.findAll();
    }

    @Override
    public Optional<Alumno> obtenerAlumnoPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    @Override
    public Alumno guardarAlumno(Alumno alumno) {
        // Aquí podrías agregar validaciones o lógica adicional antes de guardar
        return alumnoRepository.save(alumno);
    }

    @Override
    public void eliminarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }
}
