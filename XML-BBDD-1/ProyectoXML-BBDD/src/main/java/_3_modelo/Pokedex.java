package _3_modelo;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
    private int cantidadPokemonsTotales;
    private int cantidadPokemonsActual;
    private List<Pokemon> pokemonsObtenidos;

    public Pokedex(int cantidadPokemonsTotales, List<Pokemon> pokemonsObtenidos) {
        this.cantidadPokemonsTotales = cantidadPokemonsTotales;
        this.cantidadPokemonsActual = 0;
        this.pokemonsObtenidos = new ArrayList<>(pokemonsObtenidos);
    }

    public int getCantidad_pokemons_totales() {
        return cantidadPokemonsTotales;
    }

    public List<Pokemon> getPokemons_obtenidos() {
        return pokemonsObtenidos;
    }

    public void addPokemon(Pokemon pokemon) throws NullPointerException, ClassCastException, IllegalArgumentException, UnsupportedOperationException {
        if (pokemon != null) {
            pokemonsObtenidos.add(pokemon);
            cantidadPokemonsActual++;
        }
    }

    public void setPokemons_obtenidos(List<Pokemon> pokemonsObtenidos) {
        this.pokemonsObtenidos = pokemonsObtenidos;
    }

    public void setPokemon(int idx, Pokemon pokemon) {
        pokemonsObtenidos.set(idx, pokemon);
    }

    public int getCantidad_pokemons_actual() {
        return cantidadPokemonsActual;
    }

    public void setCantidad_pokemons_totales(int cantidadPokemonsTotales) {
        this.cantidadPokemonsTotales = cantidadPokemonsTotales;
    }

    private String pokemonsToString() {
        StringBuilder sb = new StringBuilder();
        for (Pokemon pokemon : pokemonsObtenidos) {
            sb.append("\n\t\t").append(pokemon.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("POKEDEX:\n\tCantidad Actual: %d\n\tCapacidad Total: %d\n\tPokemons Obtenidos:%s", cantidadPokemonsActual, cantidadPokemonsTotales, pokemonsToString());
    }
}
