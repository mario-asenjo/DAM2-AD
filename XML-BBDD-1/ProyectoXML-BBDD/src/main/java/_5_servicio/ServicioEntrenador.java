package _5_servicio;

import _3_modelo.Entrenador;
import _6_excepciones.ApplicationException;

import java.util.List;

public interface ServicioEntrenador {
    void guardarEntrenador(Entrenador entrenador) throws ApplicationException;
    List<Entrenador> listarEntrenadores() throws ApplicationException;
    Entrenador buscarEntrenadorPorId(long id) throws ApplicationException;
    Entrenador buscarEntrenadorPorNombre(String nombre) throws ApplicationException;
    void actualizarEntrenador(Entrenador entrenador) throws ApplicationException;
    void borrarPorId(long id) throws ApplicationException;
}
