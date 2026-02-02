package com.masenjo.springpersonal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El pueblo es obligatorio")
    private String pueblo;

    @Min(value = 1, message = "La edad debe ser >= 1")
    private int edad;

    @OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pokemon> pokedex = new ArrayList<>();

    public Entrenador() {}

    public Entrenador(String nombre, String pueblo, int edad) {
        this.nombre = nombre;
        this.pueblo = pueblo;
        this.edad = edad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPueblo() { return pueblo; }
    public void setPueblo(String pueblo) { this.pueblo = pueblo; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public List<Pokemon> getPokedex() { return pokedex; }
    public void setPokedex(List<Pokemon> pokedex) { this.pokedex = pokedex; }
}
