package com.masenjo.springpersonal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del movimiento es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo del movimiento es obligatorio")
    private String tipo;

    @Min(value = 0, message = "El poder debe ser >= 0")
    private int poder;

    @ManyToMany(mappedBy = "movimientos")
    private Set<Pokemon> pokemons = new HashSet<>();

    public Movimiento() {}

    public Movimiento(String nombre, String tipo, int poder) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.poder = poder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getPoder() { return poder; }
    public void setPoder(int poder) { this.poder = poder; }

    public Set<Pokemon> getPokemons() { return pokemons; }
    public void setPokemons(Set<Pokemon> pokemons) { this.pokemons = pokemons; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movimiento that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
