package _2_controlador;

import _1_vista.Consola;
import _3_modelo.Entrenador;
import _3_modelo.Pokedex;
import _5_servicio.ServicioEntrenador;
import _5_servicio.ServicioEntrenadorMySQL;

public class ControladorEntrenadorMySQL implements ControladorEntrenador {
    private final ServicioEntrenador svc;

    public ControladorEntrenadorMySQL() {
        this.svc = new ServicioEntrenadorMySQL();
    }

    @Override
    public void crearEntrenador() {
        Entrenador entrenador;
        int id;
        String nombre;
        String pueblo;
        int edad;
        Pokedex pokedex;

        Consola.mostrarFraseEndl("Introduce dato 1 de entrenador: ");
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
