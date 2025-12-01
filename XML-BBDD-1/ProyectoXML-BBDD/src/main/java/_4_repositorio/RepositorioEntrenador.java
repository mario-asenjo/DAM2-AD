package _4_repositorio;

import _3_modelo.Entrenador;
import _6_excepciones.EntidadNoEncontradaException;
import _6_excepciones.RepositorioException;

import java.util.List;

public interface RepositorioEntrenador {
    List<Entrenador> listar() throws RepositorioException;
    void guardar(Entrenador entrenador) throws RepositorioException;
    Entrenador buscarPorId(long id) throws RepositorioException, EntidadNoEncontradaException;
    Entrenador buscarPorNombre(String nombre) throws RepositorioException, EntidadNoEncontradaException;
    void actualizar(Entrenador entrenador) throws RepositorioException, EntidadNoEncontradaException;
    void borrarPorId(long id) throws RepositorioException, EntidadNoEncontradaException;
}
