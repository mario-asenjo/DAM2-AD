package _3_modelo;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
    private int cantidad_pokemons_totales;
    private List<Pokemon> pokemons_obtenidos;

    public Pokedex(int cantidad_pokemons_totales, List<Pokemon> pokemons_obtenidos) {
        this.cantidad_pokemons_totales = cantidad_pokemons_totales;
        this.pokemons_obtenidos = new ArrayList<>(pokemons_obtenidos);
    }
}
