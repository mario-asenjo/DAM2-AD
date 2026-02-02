package com.escuela.escuela.model;

import jakarta.persistence.*;

// Esta clase representa la tabla "trabajo" en la base de datos
@Entity
public class Trabajo {

    // ID autogenerado como clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    // Relación N:1 -> Muchos Trabajos pertenecen a un Alumno
    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    // Constructor vacío necesario para JPA
    public Trabajo() {}

    // Constructor con parámetros
    public Trabajo(String titulo, String descripcion, Alumno alumno) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.alumno = alumno;
    }

    // Getters y Setters (métodos de acceso y modificación)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
}
