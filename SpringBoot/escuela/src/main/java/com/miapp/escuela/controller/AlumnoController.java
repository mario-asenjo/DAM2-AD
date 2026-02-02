package com.miapp.escuela.controller;

import com.miapp.escuela.model.Alumno;
import com.miapp.escuela.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/alumnos")
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoService.obtenerTodosLosAlumnos());
        return "alumnos";
    }

    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@RequestParam String nombre, @RequestParam String email) {
        Alumno alumno = new Alumno(nombre, email);
        alumnoService.guardarAlumno(alumno);

        return "redirect:/alumnos";
    }

    @GetMapping("/alumnos/eliminar")
    public String eliminarAlumno(@RequestParam Long id) {
        alumnoService.eliminarAlumno(id);
        return "redirect:/alumnos";
    }
}
