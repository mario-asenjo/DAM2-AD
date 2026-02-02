package com.masenjo.springpersonal.service;

import com.masenjo.springpersonal.model.Pokemon;

import java.util.List;
import java.util.Optional;

public interface PokemonService {
    List<Pokemon> obtenerTodos();
    Optional<Pokemon> obtenerPorId(Long id);
    Pokemon guardar(Pokemon pokemon);
    void eliminar(Long id);

    // N:M helpers
    void anadirMovimiento(Long pokemonId, Long movimientoId);
    void quitarMovimiento(Long pokemonId, Long movimientoId);
}
