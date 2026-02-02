package com.escuela.escuela.controller;


import com.escuela.escuela.model.Alumno;
import com.escuela.escuela.model.Trabajo;
import com.escuela.escuela.service.AlumnoService;
import com.escuela.escuela.service.TrabajoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TrabajoController {

    private final TrabajoService trabajoService;
    private final AlumnoService alumnoService;

    public TrabajoController(TrabajoService trabajoService, AlumnoService alumnoService) {
        this.trabajoService = trabajoService;
        this.alumnoService = alumnoService;
    }

    @GetMapping("/trabajos")
    public String listarTrabajos(Model model) {
        model.addAttribute("trabajos", trabajoService.obtenerTodosLosTrabajos());
        model.addAttribute("alumnos", alumnoService.obtenerTodosLosAlumnos());  // Pasamos la lista de alumnos a la vista
        return "trabajos";
    }

    @PostMapping("/trabajos/guardar")
    public String guardarTrabajo(@RequestParam String titulo, @RequestParam String descripcion, @RequestParam Long alumnoId) {
        Optional<Alumno> alumnoOptional = alumnoService.obtenerAlumnoPorId(alumnoId);  // Buscamos el alumno por ID

        if (alumnoOptional.isPresent()) {
            Trabajo trabajo = new Trabajo(titulo, descripcion, alumnoOptional.get());  // Pasamos el alumno como tercer argumento
            trabajoService.guardarTrabajo(trabajo);
        }
        return "redirect:/trabajos";  // Redirige a la lista de trabajos
    }

    @GetMapping("/trabajos/eliminar")
    public String eliminarTrabajo(@RequestParam Long id) {
        trabajoService.eliminarTrabajo(id);
        return "redirect:/trabajos";
    }
}
