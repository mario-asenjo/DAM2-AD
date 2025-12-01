package _2_controlador;

import _1_vista.Colores;
import _1_vista.Consola;
import _1_vista.Escaner;
import _3_modelo.Entrenador;
import _3_modelo.Pokedex;
import _3_modelo.Pokemon;
import _5_servicio.ServicioEntrenador;
import _6_excepciones.ApplicationException;
import _6_excepciones.EntidadNoEncontradaException;
import _6_excepciones.EntradaUsuarioNoValidaException;
import _6_excepciones.IdDuplicadoException;
import _6_excepciones.RepositorioException;

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
            Consola.mostrarFraseEndl("Entrenador creado correctamente!", Colores.VERDE);
        } catch (IdDuplicadoException e) {
            Consola.mostrarExcepcion(e);
        } catch (RepositorioException e) {
            Consola.mostrarFraseEndl("Error de repositorio: " + e.getMessage(), Colores.ROJO);
        } catch (ApplicationException e) {
            Consola.mostrarFraseEndl("Error en la app: " + e.getMessage(), Colores.ROJO);
        } catch (Exception e) {
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
        } catch (EntradaUsuarioNoValidaException e) {
            Consola.mostrarFraseEndl("Error introduciendo datos: " + e.getMessage(), Colores.ROJO);
        } catch (ApplicationException e) {
            Consola.mostrarFraseEndl("Error buscando entrenador: " + e.getMessage(), Colores.ROJO);
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
        } catch (EntradaUsuarioNoValidaException e) {
            Consola.mostrarFraseEndl("Error introduciendo datos: " + e.getMessage(), Colores.ROJO);
        } catch (ApplicationException e) {
            Consola.mostrarFraseEndl("Error buscando entrenador: " + e.getMessage(), Colores.ROJO);
        }
    }

    @Override
    public void borrarEntrenador() {
        int id;

        Consola.mostrarFraseEndl("##### MENÚ BORRADO ENTRENADOR POKEMON ##### ");
        try {
            id = Escaner.pedirEntero("Introduce un ID: ");
            servicioEntrenador.borrarPorId(id);
        } catch (EntradaUsuarioNoValidaException e) {
            Consola.mostrarFraseEndl("Error introduciendo datos: " + e.getMessage(), Colores.ROJO);
        } catch (ApplicationException e) {
            Consola.mostrarFraseEndl("Error buscando entrenador: " + e.getMessage(), Colores.ROJO);
        }
    }

    private Entrenador nuevoEntrenador(boolean actualizar) throws ApplicationException {
        int id = -1;
        String nombre = null;
        String pueblo = null;
        int edad = -1;

            id = Escaner.pedirEntero("Introduce un ID: ");
            if (actualizar) {
                servicioEntrenador.buscarEntrenadorPorId(id);
            }
            nombre = Escaner.pedirString("Introduce un nombre: ");
            pueblo = Escaner.pedirString("Introduce un pueblo: ");
            edad = Escaner.pedirEntero("Introduce una edad: ");
        return new Entrenador(id, nombre, pueblo, edad);
    }

    private Pokemon nuevoPokemon() throws ApplicationException {
        int id = -1;
        String nombre = null;
        String especie = null;
        String tipo = null;
        int nivel = -1;

        id = Escaner.pedirEntero("Introduce le ID del pokemon: ");
        nombre = Escaner.pedirString("Introduce el nombre del pokemon: ");
        especie = Escaner.pedirString("Introduce la especie del pokemon: ");
        tipo = Escaner.pedirString("Introduce el tipo del pokemon: ");
        nivel = Escaner.pedirEntero("Introduce el nivel del pokemon: ");
        return new Pokemon(id, nombre, especie, tipo, nivel);
    }

    private Pokedex rutinaInsertarPokemons(long idEntrenador, boolean actualizar) throws ApplicationException {
        boolean quiereIntroducirPokemon;
        Pokedex pokedex = new Pokedex(151, new ArrayList<Pokemon>());
        String frase;
        boolean pokedexAntigua = false;
        Pokemon pokemon;

        if (actualizar) {
            pokedexAntigua = Escaner.pedirBoolean("¿Quieres usar la pokedex antigua de este entrenador?");
        }
        pokedex = pokedexAntigua ? servicioEntrenador.buscarEntrenadorPorId(idEntrenador).getPokedex() : pokedex;
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
