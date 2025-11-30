package _1_vista;

import _2_controlador.*;
import _3_modelo.Pokedex;

import java.util.List;

public class PuntoEntrada {
    private final ControladorEntrenador controlador;

    public PuntoEntrada() {
        Consola.mostrarFraseEndl("Elige el repositorio a utilizar:", Colores.VERDE);
        Consola.mostrarMenu(List.of("Repo XML.", "Repo MySQL.", "Repo JSON.", "Repo MongoDB."));
        int opcionRepo = Escaner.pedirEntero("Introduce tu opcion: ");
        switch (opcionRepo) {
            case 1 -> controlador = new ControladorEntrenadorXML();
            case 2 -> controlador = new ControladorEntrenadorMySQL();
            case 3 -> controlador = new ControladorEntrenadorJSON();
            case 4 -> controlador = new ControladorEntrenadorMongoDB();
            default -> controlador = null;
        }
    }

    public void iniciar() {
        boolean salir;
        int opcion_operacion;

        if (controlador == null) {
            Consola.mostrarFraseEndl("No has elegido un repositorio correcto. Saliendo del programa.", Colores.ROJO);
        } else {
            salir = false;

            Consola.mostrarFraseEndl("##### PROGRAMA DE ENTRENADORES POKEMON #####");
            do {
                Consola.mostrarFraseEndl("OPERACIONES: ");
                Consola.mostrarMenu(List.of("Crear entrenador pokemon.", "Buscar un entrenador pokemon.", "Actualizar datos de entrenador pokemon.", "Borrar entrenador pokemon.", "Salir del programa."));
                opcion_operacion = Escaner.pedirEntero("Introduce tu opcion: ");
                switch (opcion_operacion) {
                    case 1 -> controlador.crearEntrenador();
                    case 2 -> controlador.buscarEntrenador();
                    case 3 -> controlador.actualizarEntrenador();
                    case 4 -> controlador.borrarEntrenador();
                    case 5 -> salir = true;
                }
            } while (!salir);
            Consola.mostrarFraseEndl("Saliendo del programa gracias!", Colores.VERDE);
        }
    }
}
