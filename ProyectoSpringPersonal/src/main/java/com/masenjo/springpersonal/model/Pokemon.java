package com.masenjo.springpersonal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @Min(value = 1, message = "El nivel debe ser >= 1")
    private int nivel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "entrenador_id", nullable = false)
    @NotNull(message = "Debes seleccionar un entrenador")
    private Entrenador entrenador;

    @ManyToMany
    @JoinTable(
            name = "pokemon_movimiento",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "movimiento_id")
    )
    private Set<Movimiento> movimientos = new HashSet<>();

    public Pokemon() {}

    public Pokemon(String nombre, String especie, String tipo, int nivel, Entrenador entrenador) {
        this.nombre = nombre;
        this.especie = especie;
        this.tipo = tipo;
        this.nivel = nivel;
        this.entrenador = entrenador;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }

    public Set<Movimiento> getMovimientos() { return movimientos; }
    public void setMovimientos(Set<Movimiento> movimientos) { this.movimientos = movimientos; }
}
