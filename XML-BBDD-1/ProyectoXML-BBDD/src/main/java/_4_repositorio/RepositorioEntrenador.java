package _4_repositorio;

import _3_modelo.Entrenador;

import java.util.List;

public interface RepositorioEntrenador {
    List<Entrenador> listar() throws Exception;
    void guardar(Entrenador entrenador) throws Exception;
    Entrenador buscarPorId(long id) throws Exception;
    Entrenador buscarPorNombre(String nombre) throws Exception;
    void actualizar(Entrenador entrenador) throws Exception;
    void borrarPorId(long id) throws Exception;
}
