package _3_modelo;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
    private int cantidad_pokemons_totales;
    private int cantidad_pokemons_actual;
    private List<Pokemon> pokemons_obtenidos;

    public Pokedex(int cantidad_pokemons_totales, List<Pokemon> pokemons_obtenidos) {
        this.cantidad_pokemons_totales = cantidad_pokemons_totales;
        this.cantidad_pokemons_actual = 0;
        this.pokemons_obtenidos = new ArrayList<>(pokemons_obtenidos);
    }

    public int getCantidad_pokemons_totales() {
        return cantidad_pokemons_totales;
    }

    public List<Pokemon> getPokemons_obtenidos() {
        return pokemons_obtenidos;
    }

    public void addPokemon(Pokemon pokemon) throws NullPointerException, ClassCastException, IllegalArgumentException, UnsupportedOperationException {
        pokemons_obtenidos.add(pokemon);
        cantidad_pokemons_actual++;
    }

    public void setPokemons_obtenidos(List<Pokemon> pokemons_obtenidos) {
        this.pokemons_obtenidos = pokemons_obtenidos;
    }

    public int getCantidad_pokemons_actual() {
        return cantidad_pokemons_actual;
    }

    public void setCantidad_pokemons_actual(int cantidad_pokemons_actual) {
        this.cantidad_pokemons_actual = cantidad_pokemons_actual;
    }

    public void setCantidad_pokemons_totales(int cantidad_pokemons_totales) {
        this.cantidad_pokemons_totales = cantidad_pokemons_totales;
    }

    private String pokemonsToString() {
        StringBuilder sb = new StringBuilder();
        for (Pokemon pokemon : pokemons_obtenidos) {
            sb.append(pokemon.toString()).append("\n\t");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("POKEDEX:\n\tCantidad Actual: %d\n\tCapacidad Total: %d\n\tPokemons Obtenidos:\n\t%s", cantidad_pokemons_actual, cantidad_pokemons_totales, pokemonsToString());
    }
}
