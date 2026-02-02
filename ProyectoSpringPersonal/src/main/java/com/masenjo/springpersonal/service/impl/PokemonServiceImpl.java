package com.masenjo.springpersonal.service.impl;

import com.masenjo.springpersonal.model.Movimiento;
import com.masenjo.springpersonal.model.Pokemon;
import com.masenjo.springpersonal.repository.MovimientoRepository;
import com.masenjo.springpersonal.repository.PokemonRepository;
import com.masenjo.springpersonal.service.PokemonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepo;
    private final MovimientoRepository movimientoRepo;

    public PokemonServiceImpl(PokemonRepository pokemonRepo, MovimientoRepository movimientoRepo) {
        this.pokemonRepo = pokemonRepo;
        this.movimientoRepo = movimientoRepo;
    }

    @Override
    public List<Pokemon> obtenerTodos() { return pokemonRepo.findAll(); }

    @Override
    public Optional<Pokemon> obtenerPorId(Long id) { return pokemonRepo.findById(id); }

    @Override
    public Pokemon guardar(Pokemon pokemon) { return pokemonRepo.save(pokemon); }

    @Override
    public void eliminar(Long id) { pokemonRepo.deleteById(id); }

    @Transactional
    @Override
    public void anadirMovimiento(Long pokemonId, Long movimientoId) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon no encontrado"));
        Movimiento mov = movimientoRepo.findById(movimientoId)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));

        pokemon.getMovimientos().add(mov);
        // no hace falta save explícito por @Transactional + dirty checking
    }

    @Transactional
    @Override
    public void quitarMovimiento(Long pokemonId, Long movimientoId) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon no encontrado"));
        Movimiento mov = movimientoRepo.findById(movimientoId)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));

        pokemon.getMovimientos().remove(mov);
    }
}
