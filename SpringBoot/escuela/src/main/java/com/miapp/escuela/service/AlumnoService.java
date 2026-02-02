package com.miapp.escuela.service;

import com.miapp.escuela.model.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {
    List<Alumno> obtenerTodosLosAlumnos();
    Optional<Alumno> obtenerAlumnoPorId(Long id);

    Alumno guardarAlumno(Alumno alumno);
    void eliminarAlumno(Long id);
}
