package com.masenjo.springpersonal.controller;

import com.masenjo.springpersonal.model.Pokemon;
import com.masenjo.springpersonal.service.EntrenadorService;
import com.masenjo.springpersonal.service.MovimientoService;
import com.masenjo.springpersonal.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;
    private final EntrenadorService entrenadorService;
    private final MovimientoService movimientoService;

    public PokemonController(PokemonService pokemonService,
                             EntrenadorService entrenadorService,
                             MovimientoService movimientoService) {
        this.pokemonService = pokemonService;
        this.entrenadorService = entrenadorService;
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pokemons", pokemonService.obtenerTodos());
        return "pokemons/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pokemon", new Pokemon());
        model.addAttribute("entrenadores", entrenadorService.obtenerTodos());
        model.addAttribute("modo", "crear");
        model.addAttribute("actionUrl", "/pokemons");
        return "pokemons/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("pokemon") Pokemon pokemon,
                        BindingResult br,
                        Model model) {
        if (br.hasErrors()) {
            model.addAttribute("entrenadores", entrenadorService.obtenerTodos());
            model.addAttribute("modo", "crear");
            return "pokemons/form";
        }
        pokemonService.guardar(pokemon);
        return "redirect:/pokemons";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Pokemon pokemon = pokemonService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon no encontrado"));
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("entrenadores", entrenadorService.obtenerTodos());
        model.addAttribute("modo", "editar");
        model.addAttribute("actionUrl", "/pokemons/" + id);
        return "pokemons/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("pokemon") Pokemon pokemon,
                             BindingResult br,
                             Model model) {
        if (br.hasErrors()) {
            model.addAttribute("entrenadores", entrenadorService.obtenerTodos());
            model.addAttribute("modo", "editar");
            return "pokemons/form";
        }
        pokemon.setId(id);
        pokemonService.guardar(pokemon);
        return "redirect:/pokemons";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        pokemonService.eliminar(id);
        return "redirect:/pokemons";
    }

    // --- Pantalla para gestionar N:M (Movimientos del Pokémon)
    @GetMapping("/{id}/movimientos")
    public String movimientos(@PathVariable Long id, Model model) {
        Pokemon pokemon = pokemonService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon no encontrado"));

        model.addAttribute("pokemon", pokemon);
        model.addAttribute("movimientos", movimientoService.obtenerTodos());
        return "pokemons/movimientos";
    }

    @PostMapping("/{id}/movimientos/anadir")
    public String anadirMovimiento(@PathVariable Long id,
                                   @RequestParam Long movimientoId) {
        pokemonService.anadirMovimiento(id, movimientoId);
        return "redirect:/pokemons/" + id + "/movimientos";
    }

    @PostMapping("/{id}/movimientos/quitar")
    public String quitarMovimiento(@PathVariable Long id,
                                   @RequestParam Long movimientoId) {
        pokemonService.quitarMovimiento(id, movimientoId);
        return "redirect:/pokemons/" + id + "/movimientos";
    }
}
