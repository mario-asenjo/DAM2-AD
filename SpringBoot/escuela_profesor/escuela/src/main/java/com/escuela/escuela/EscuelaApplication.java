package com.escuela.escuela;

import com.escuela.escuela.model.Alumno;
import com.escuela.escuela.model.Trabajo;
import com.escuela.escuela.repository.AlumnoRepository;
import com.escuela.escuela.repository.TrabajoRepository;
import com.escuela.escuela.service.AlumnoService;
import com.escuela.escuela.service.TrabajoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class EscuelaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscuelaApplication.class, args);
	}

	@Bean
	public CommandLineRunner datosDePrueba(AlumnoService alumnoService, TrabajoService trabajoService) {
		return args -> {
			// 1. Crear y guardar alumnos
			Alumno alumno1 = new Alumno("Peter Parker", "peterp@escuela.com");
			Alumno alumno2 = new Alumno("Mary Jane Watson", "maryjanew@escuela.com");

			alumnoService.guardarAlumno(alumno1);
			alumnoService.guardarAlumno(alumno2);

			// 2. Crear y guardar trabajos asociados a los alumnos
			Trabajo trabajo1 = new Trabajo("Proyecto de Ciencias", "Teleraña artificial", alumno1);
			Trabajo trabajo2 = new Trabajo("Trabajo de Física", "Estudio del balanceo", alumno1);
			Trabajo trabajo3 = new Trabajo("Ensayo de Periodismo", "Portadas históricas de DB", alumno2);

			trabajoService.guardarTrabajo(trabajo1);
			trabajoService.guardarTrabajo(trabajo2);
			trabajoService.guardarTrabajo(trabajo3);

			// 3. Mostrar todos los alumnos
			System.out.println("Lista de alumnos:");
			alumnoService.obtenerTodosLosAlumnos().forEach(alumno -> {
				System.out.println("Alumno: " + alumno.getNombre() + ", Email: " + alumno.getEmail());
			});

			// 4. Mostrar todos los trabajos
			System.out.println("Lista de trabajos:");
			trabajoService.obtenerTodosLosTrabajos().forEach(trabajo -> {
				System.out.println("Trabajo: " + trabajo.getTitulo() + ", Alumno: " + trabajo.getAlumno().getNombre());
			});

			// 5. Buscar trabajos por título
			System.out.println("Trabajos que contienen 'Periodismo':");
			trabajoService.buscarPorTitulo("Periodismo").forEach(trabajo -> {
				System.out.println("Trabajo: " + trabajo.getTitulo());
			});

			// 6. Obtener alumno por ID (con Optional)
			Optional<Alumno> alumnoEncontrado = alumnoService.obtenerAlumnoPorId(3L);
			if (alumnoEncontrado.isPresent()) {
				System.out.println("Alumno encontrado: " + alumnoEncontrado.get().getNombre());
			} else {
				System.out.println("Alumno no encontrado.");
			}
		};
	}
}
