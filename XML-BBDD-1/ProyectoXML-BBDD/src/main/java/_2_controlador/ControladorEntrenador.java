package _2_controlador;

import _5_servicio.ServicioEntrenador;

public interface ControladorEntrenador {
    void crearEntrenador();
    void buscarEntrenador();
    void actualizarEntrenador();
    void borrarEntrenador();
    void comunicarConOtroRepositorio(ServicioEntrenador servicioDestino);
}
