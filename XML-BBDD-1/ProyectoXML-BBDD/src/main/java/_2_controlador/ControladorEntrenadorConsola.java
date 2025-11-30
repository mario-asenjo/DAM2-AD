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

public class ControladorEntrenadorConsola implements ControladorEntrenador {

    private final ServicioEntrenador servicioEntrenador;

    public ControladorEntrenadorConsola(ServicioEntrenadorImpl servicioEntrenador) {
        this.servicioEntrenador = servicioEntrenador;
    }

    @Override
    public void crearEntrenador() throws Exception {
        int id;
        String nombre;
        String pueblo;
        int edad;
        Pokedex pokedex;
        Entrenador entrenador;

        Consola.mostrarFraseEndl("##### MENU CREACIÓN ENTRENADOR POKEMON #####", Colores.VERDE);
        id = Escaner.pedirEntero("Introduce un ID: ");
        nombre = Escaner.pedirString("Introduce un nombre: ");
        pueblo = Escaner.pedirString("Introduce un pueblo: ");
        edad = Escaner.pedirEntero("Introduce una edad: ");

        pokedex = new Pokedex(151, new ArrayList<Pokemon>());
        entrenador = new Entrenador(id, nombre, pueblo, edad, pokedex);
        servicioEntrenador.guardarEntrenador(entrenador);
    }

    @Override
    public void buscarEntrenador() {

    }

    @Override
    public void actualizarEntrenador() {

    }

    @Override
    public void borrarEntrenador() {

    }
}
