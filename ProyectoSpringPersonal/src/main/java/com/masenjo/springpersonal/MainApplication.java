package com.masenjo.springpersonal;

import com.masenjo.springpersonal.model.Entrenador;
import com.masenjo.springpersonal.model.Movimiento;
import com.masenjo.springpersonal.model.Pokemon;
import com.masenjo.springpersonal.service.EntrenadorService;
import com.masenjo.springpersonal.service.MovimientoService;
import com.masenjo.springpersonal.service.PokemonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public CommandLineRunner seed(EntrenadorService entrenadorService,
                                  PokemonService pokemonService,
                                  MovimientoService movimientoService) {
        return args -> {
            var ash = entrenadorService.guardar(new Entrenador("Ash", "Pueblo Paleta", 10));
            var misty = entrenadorService.guardar(new Entrenador("Misty", "Ciudad Celeste", 12));

            var placaje = movimientoService.guardar(new Movimiento("Placaje", "Normal", 40));
            var impacto = movimientoService.guardar(new Movimiento("Impactrueno", "Eléctrico", 40));
            var ascuas = movimientoService.guardar(new Movimiento("Ascuas", "Fuego", 40));

            var pikachu = pokemonService.guardar(new Pokemon("Pikachu", "Ratón", "Eléctrico", 12, ash));
            var charmander = pokemonService.guardar(new Pokemon("Charmander", "Lagartija", "Fuego", 10, ash));
            var staryu = pokemonService.guardar(new Pokemon("Staryu", "Estrella", "Agua", 9, misty));

            // N:M
            pokemonService.anadirMovimiento(pikachu.getId(), placaje.getId());
            pokemonService.anadirMovimiento(pikachu.getId(), impacto.getId());
            pokemonService.anadirMovimiento(charmander.getId(), placaje.getId());
            pokemonService.anadirMovimiento(charmander.getId(), ascuas.getId());
            pokemonService.anadirMovimiento(staryu.getId(), placaje.getId());
        };
    }

}
