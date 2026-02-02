package com.escuela.escuela.model;

import jakarta.persistence.*;
import java.util.List;

// Esta clase representa la tabla "alumno" en la base de datos
@Entity
public class Alumno {

    // ID autogenerado como clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    // Relación 1:N -> Un Alumno tiene muchos Trabajos
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trabajo> trabajos;

    // Constructor vacío necesario para JPA
    public Alumno() {}

    // Constructor con parámetros
    public Alumno(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters (métodos de acceso y modificación)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Trabajo> getTrabajos() { return trabajos; }
    public void setTrabajos(List<Trabajo> trabajos) { this.trabajos = trabajos; }
}
