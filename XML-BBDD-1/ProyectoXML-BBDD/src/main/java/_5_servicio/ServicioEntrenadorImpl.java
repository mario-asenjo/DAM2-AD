package _5_servicio;

import _3_modelo.Entrenador;
import _4_repositorio.*;
import _6_excepciones.ApplicationException;
import _6_excepciones.EntidadNoEncontradaException;
import _6_excepciones.IdDuplicadoException;
import _6_excepciones.RepositorioException;

import java.util.List;

public class ServicioEntrenadorImpl implements ServicioEntrenador {
    private final RepositorioEntrenador repo;

    public ServicioEntrenadorImpl(RepositorioEntrenador repo) {
        this.repo = repo;
    }

    @Override
    public void guardarEntrenador(Entrenador entrenador) throws ApplicationException {
        try {
            repo.buscarPorId(entrenador.getId());
            throw new IdDuplicadoException("El entrenador con este ID ya existe.");
        } catch (EntidadNoEncontradaException e) {
            repo.guardar(entrenador);
        }
    }

    @Override
    public List<Entrenador> listarEntrenadores() throws ApplicationException {
            return repo.listar();
    }

    @Override
    public Entrenador buscarEntrenadorPorId(long id) throws ApplicationException {
            return repo.buscarPorId(id);
    }

    @Override
    public Entrenador buscarEntrenadorPorNombre(String nombre) throws ApplicationException {
            return repo.buscarPorNombre(nombre);
    }

    @Override
    public void actualizarEntrenador(Entrenador entrenador) throws ApplicationException {
            repo.actualizar(entrenador);
    }

    @Override
    public void borrarPorId(long id) throws ApplicationException {
            repo.borrarPorId(id);
    }
}
