package _5_servicio;

import _3_modelo.Entrenador;
import _4_repositorio.*;

import java.util.List;

public class ServicioEntrenadorImpl implements ServicioEntrenador {
    private final RepositorioEntrenador repo;

    public ServicioEntrenadorImpl(RepositorioEntrenador repo) {
        this.repo = repo;
    }

    @Override
    public void guardarEntrenador(Entrenador entrenador) throws Exception {
        if (repo.buscarPorId(entrenador.getId()) != null) {
            throw new Exception("El usuario con ese ID ya existe!");
        }
        repo.guardar(entrenador);
    }

    @Override
    public List<Entrenador> listarEntrenadores() throws Exception {
        return repo.listar();
    }

    @Override
    public Entrenador buscarEntrenadorPorId(long id) throws Exception {
        return repo.buscarPorId(id);
    }

    @Override
    public Entrenador buscarEntrenadorPorNombre(String nombre) throws Exception {
        return repo.buscarPorNombre(nombre);
    }

    @Override
    public void actualizarEntrenador(Entrenador entrenador) throws Exception {
        repo.actualizar(entrenador);
    }

    @Override
    public void borrarPorId(long id) throws Exception {
        repo.borrarPorId(id);
    }
}
