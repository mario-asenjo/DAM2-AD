package com.miapp.escuela;

import com.miapp.escuela.model.Alumno;
import com.miapp.escuela.model.Trabajo;
import com.miapp.escuela.repository.AlumnoRepository;
import com.miapp.escuela.repository.TrabajoRepository;
import com.miapp.escuela.service.AlumnoService;
import com.miapp.escuela.service.TrabajoService;
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

	/**@Bean
	public CommandLineRunner datosDePrueba(AlumnoRepository alumnoRepository, TrabajoRepository trabajoRepository) {
		return args -> {
			// Crear un alumno
			Alumno alumno1 = new Alumno("Lola Perez", "lola.perez@escuela.com");
			alumnoRepository.save(alumno1);
			// Crear trabajos para el alumno
			Trabajo trabajo1 = new Trabajo("Trabajo de BD", "Usando JOINS", alumno1);
			Trabajo trabajo2 = new Trabajo("Trabajo de AD", "Hola Spring Boot", alumno1);
			trabajoRepository.save(trabajo1);
			trabajoRepository.save(trabajo2);
			System.out.println("Lista de alumnos:");
			alumnoRepository.findAll().forEach(alumno -> {
				System.out.println("Alumno:" + alumno.getNombre() + ",Email:" + alumno.getEmail());
			});
			System.out.println("Trabajos que contienen 'AD':");
			trabajoRepository.findByTituloContaining("AD").forEach(trabajo -> {
				System.out.println("Trabajo:" + trabajo.getTitulo() + ",Alumno:" + trabajo.getAlumno().getNombre());
			});

		};
	}
	*/

	@Bean
	public CommandLineRunner datosDePruebaServicio(AlumnoService alumnoService, TrabajoService trabajoService) {
		return args -> {
			Alumno alumno1 = new Alumno("Peter Parker", "peterp@escuela.com");
			Alumno alumno2 = new Alumno("Mary Jane Watson", "maryjanew@escuela.com");

			alumnoService.guardarAlumno(alumno1);
			alumnoService.guardarAlumno(alumno2);

			Trabajo trabajo1 = new Trabajo("Proyecto de Ciencias", "Telaraña artificial", alumno1);
			Trabajo trabajo2 = new Trabajo("Trabajo de Fisica", "Estudio del balanceo", alumno1);
			Trabajo trabajo3 = new Trabajo("Ensayo de Periodismo", "Portadas historicas de DB", alumno2);

			trabajoService.guardarTrabajo(trabajo1);
			trabajoService.guardarTrabajo(trabajo2);
			trabajoService.guardarTrabajo(trabajo3);

			System.out.println("Lista de alumnos:");
			alumnoService.obtenerTodosLosAlumnos().forEach(alumno -> {
				System.out.println("Alumno:" + alumno.getNombre() + "Email:" + alumno.getEmail());
			});

			System.out.println("Lista de trabajos.");
			trabajoService.obtenerTodosLosTrabajos().forEach(trabajo -> {
				System.out.println("Trabajo:" + trabajo.getTitulo() + ",Alumno:" + trabajo.getAlumno().getNombre());
			});

			System.out.println("Trabajos que contienen 'Física':");
			trabajoService.buscarPorTitulo("Física").forEach(trabajo -> {
				System.out.println("Trabajo:" + trabajo.getTitulo());
			});

			Optional<Alumno> alumnoEncontrado = alumnoService.obtenerAlumnoPorId(1L);
			if (alumnoEncontrado.isPresent()) {
				System.out.println("Alumno encontrado:" + alumnoEncontrado.get().getNombre());
			} else {
				System.out.println("Alumno no encontrado.");
			}
		};
	}
}
