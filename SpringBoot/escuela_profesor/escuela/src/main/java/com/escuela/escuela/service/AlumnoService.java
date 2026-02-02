package com.escuela.escuela.service;


import com.escuela.escuela.model.Alumno;
import java.util.List;
import java.util.Optional;

// Definimos la interfaz del servicio para Alumno
public interface AlumnoService {
    List<Alumno> obtenerTodosLosAlumnos();
    Optional<Alumno> obtenerAlumnoPorId(Long id);
    Alumno guardarAlumno(Alumno alumno);
    void eliminarAlumno(Long id);
}

