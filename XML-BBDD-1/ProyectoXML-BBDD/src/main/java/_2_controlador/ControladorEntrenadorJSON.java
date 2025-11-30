package _2_controlador;

import _1_vista.Consola;
import _5_servicio.ServicioEntrenador;
import _5_servicio.ServicioEntrenadorJSON;

public class ControladorEntrenadorJSON implements ControladorEntrenador{
    private final ServicioEntrenador svc;

    public ControladorEntrenadorJSON(String ficheroEntrada, String ficheroSalida) {
        this.svc = new ServicioEntrenadorJSON(ficheroEntrada, ficheroSalida);
    }

    @Override
    public void crearEntrenador() {
        Consola.mostrarFraseEndl("Introduce dato 1 de entrenaodr: ");
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
