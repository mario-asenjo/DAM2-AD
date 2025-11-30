package _2_controlador;

import _1_vista.Colores;
import _1_vista.Consola;
import _1_vista.Escaner;
import _3_modelo.Entrenador;
import _3_modelo.Pokedex;
import _3_modelo.Pokemon;
import _5_servicio.ServicioEntrenador;
import _5_servicio.ServicioEntrenadorImpl;

import java.util.ArrayList;
import java.util.List;

public class ControladorEntrenadorConsola implements ControladorEntrenador {

    private final ServicioEntrenador servicioEntrenador;

    public ControladorEntrenadorConsola(ServicioEntrenador servicioEntrenador) {
        this.servicioEntrenador = servicioEntrenador;
    }

    @Override
    public void crearEntrenador() {
        Pokedex pokedex;
        Entrenador entrenador;

        Consola.mostrarFraseEndl("##### MENU CREACIÓN ENTRENADOR POKEMON #####", Colores.VERDE);
        try {
            entrenador = nuevoEntrenador(false);
            pokedex = rutinaInsertarPokemons(entrenador.getId(), false);
            entrenador.setPokedex(pokedex);
            servicioEntrenador.guardarEntrenador(entrenador);
        } catch (Exception  e) {
            Consola.mostrarExcepcion(e);
        }
    }

    @Override
    public void buscarEntrenador() {
        int opcion;
        int id;
        String nombre;
        Entrenador entrenador;

        Consola.mostrarFraseEndl("##### MENU BÚSQUEDA ENTRENADOR POKEMON #####");
        try {
            Consola.mostrarMenu(List.of("Buscar por ID.", "Buscar por nombre."));
            opcion = Escaner.pedirEntero("Introduce tu opcion: ");
            switch (opcion) {
                case 1:
                    id = Escaner.pedirEntero("Introduce el ID: ");
                    entrenador = servicioEntrenador.buscarEntrenadorPorId(id);
                    if (entrenador != null) {
                        Consola.mostrarFraseEndl(entrenador.toString());
                    } else {
                        Consola.mostrarFraseEndl("No se encuentra usuario con ese ID.", Colores.ROJO);
                    }
                    break;
                case 2:
                    nombre = Escaner.pedirString("Introduce el nombre: ");
                    entrenador = servicioEntrenador.buscarEntrenadorPorNombre(nombre);
                    if (entrenador != null) {
                        Consola.mostrarFraseEndl(entrenador.toString());
                    } else {
                        Consola.mostrarFraseEndl("No se encuentra usuario con ese nombre.", Colores.ROJO);
                    }
                    break;
                default:
                    Consola.mostrarFraseEndl("No se permite otra opcion que no sea 1 o 2.");
            }
        } catch (Exception e) {
            Consola.mostrarExcepcion(e);
        }
    }

    @Override
    public void actualizarEntrenador() {
        Pokedex pokedex;
        Entrenador entrenador;

        Consola.mostrarFraseEndl("##### MENU ACTUALIZACIÓN DE ENTRENADOR POKEMON #####", Colores.VERDE);
        try {
            entrenador = nuevoEntrenador(true);
            pokedex = rutinaInsertarPokemons(entrenador.getId(), true);
            entrenador.setPokedex(pokedex);
            servicioEntrenador.actualizarEntrenador(entrenador);
        } catch (Exception  e) {
            Consola.mostrarExcepcion(e);
        }
    }

    @Override
    public void borrarEntrenador() {
        int id;

        Consola.mostrarFraseEndl("##### MENÚ BORRADO ENTRENADOR POKEMON ##### ");
        id = Escaner.pedirEntero("Introduce un ID: ");
        try {
            servicioEntrenador.borrarPorId(id);
        } catch (Exception e) {
            Consola.mostrarExcepcion(e);
        }
    }

    private Entrenador nuevoEntrenador(boolean actualizar) throws Exception {
        int id;
        String nombre;
        String pueblo;
        int edad;

        id = Escaner.pedirEntero("Introduce un ID: ");
        if (actualizar) {
            if (servicioEntrenador.buscarEntrenadorPorId(id) == null) {
                throw new Exception("No existe entrenador con este ID.");
            }
        }
        nombre = Escaner.pedirString("Introduce un nombre: ");
        pueblo = Escaner.pedirString("Introduce un pueblo: ");
        edad = Escaner.pedirEntero("Introduce una edad: ");
        return new Entrenador(id, nombre, pueblo,  edad);
    }

    private Pokemon nuevoPokemon() {
        int id;
        String nombre;
        String especie;
        String tipo;
        int nivel;

        id = Escaner.pedirEntero("Introduce le ID del pokemon: ");
        nombre = Escaner.pedirString("Introduce el nombre del pokemon: ");
        especie = Escaner.pedirString("Introduce la especie del pokemon: ");
        tipo = Escaner.pedirString("Introduce el tipo del pokemon: ");
        nivel = Escaner.pedirEntero("Introduce el nivel del pokemon: ");
        return new Pokemon(id, nombre, especie, tipo, nivel);
    }

    private Pokedex rutinaInsertarPokemons(long idEntrenador, boolean actualizar) throws Exception {
        boolean quiereIntroducirPokemon;
        Pokedex pokedex;
        String frase;
        boolean pokedexAntigua = false;
        Pokemon pokemon;

        if (actualizar) {
            pokedexAntigua = Escaner.pedirBoolean("¿Quieres usar la pokedex antigua de este entrenador?");
        }
        pokedex = pokedexAntigua ? servicioEntrenador.buscarEntrenadorPorId(idEntrenador).getPokedex() : new Pokedex(151, new ArrayList<Pokemon>());
        if (pokedexAntigua) {
            Consola.mostrarFraseEndl(pokedex.toString());
            frase = "¿Quieres actualizar pokemon de esta pokedex?";
        } else {
            frase = "¿Quieres introducir pokemon en esta pokedex?";
        }
        do {
            quiereIntroducirPokemon = Escaner.pedirBoolean(frase);
            pokemon = quiereIntroducirPokemon ? nuevoPokemon() : null;
            if (pokedexAntigua) {
                if (pokemon != null) {
                    pokedex.setPokemon(pokemon.getId(), pokemon);
                }
            } else {
                if (pokemon != null) {
                    pokedex.addPokemon(pokemon);
                }
            }
        } while (quiereIntroducirPokemon);
        return pokedex;
    }
}
