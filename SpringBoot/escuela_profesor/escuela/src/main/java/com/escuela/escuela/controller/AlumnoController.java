package com.escuela.escuela.controller;

import com.escuela.escuela.model.Alumno;
import com.escuela.escuela.service.AlumnoService;
import org.springframework.stereotype.Controller;  // Anotación para marcar esta clase como controlador
import org.springframework.ui.Model;  // Permite pasar datos desde el controlador a la vista
import org.springframework.web.bind.annotation.GetMapping;  // Maneja solicitudes GET (navegar a una URL)
import org.springframework.web.bind.annotation.PostMapping; // Maneja solicitudes POST (formulario)
import org.springframework.web.bind.annotation.RequestParam; // Captura parámetros de la URL o formulario

@Controller  // Indica que esta clase es un controlador web
public class AlumnoController {

    private final AlumnoService alumnoService;

    // Constructor para inyectar el servicio de alumno
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // Maneja la solicitud GET a /alumnos
    @GetMapping("/alumnos")
    public String listarAlumnos(Model model) {
        // Agrega la lista de alumnos como atributo del modelo para pasarla a la vista
        model.addAttribute("alumnos", alumnoService.obtenerTodosLosAlumnos());

        // Retorna el nombre de la vista (alumnos.html) que se encuentra en src/main/resources/templates
        return "alumnos";
    }

    // Maneja la solicitud POST al formulario de /alumnos/guardar
    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@RequestParam String nombre, @RequestParam String email) {
        // Crea un nuevo objeto Alumno con los datos del formulario y lo guarda en la base de datos
        Alumno alumno = new Alumno(nombre, email);
        alumnoService.guardarAlumno(alumno);

        // Redirige a la página de lista de alumnos después de guardar
        return "redirect:/alumnos";
    }

    // Maneja la solicitud GET para eliminar un alumno por ID
    @GetMapping("/alumnos/eliminar")
    public String eliminarAlumno(@RequestParam Long id) {
        // Llama al servicio para eliminar el alumno por su ID
        alumnoService.eliminarAlumno(id);

        // Redirige a la página de lista de alumnos después de eliminar
        return "redirect:/alumnos";
    }
}
