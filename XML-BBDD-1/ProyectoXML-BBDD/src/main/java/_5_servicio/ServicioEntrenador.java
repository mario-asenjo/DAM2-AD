package _5_servicio;

import _3_modelo.Entrenador;

import java.util.List;

public interface ServicioEntrenador {
    void guardarEntrenador(Entrenador entrenador) throws Exception;
    List<Entrenador> listarEntrenadores() throws Exception;
    Entrenador buscarEntrenadorPorId(long id) throws Exception;
    Entrenador buscarEntrenadorPorNombre(String nombre) throws Exception;
    void actualizarEntrenador(Entrenador entrenador) throws Exception;
    void borrarPorId(long id) throws Exception;
}
