package _4_repositorio;

import _3_modelo.Entrenador;
import _4_repositorio.conn_utils.MySQLUtils;

import javax.sql.DataSource;
import java.util.List;

public class RepositorioEntrenadorMySQL implements RepositorioEntrenador {
    private MySQLUtils myUtils;

    public RepositorioEntrenadorMySQL(DataSource dataSource) {
        this.myUtils = new MySQLUtils(dataSource);
    }


    @Override
    public List<Entrenador> listar() throws Exception {
        return List.of();
    }

    @Override
    public void guardar(Entrenador entrenador) throws Exception {

        /*
        * UNA VEZ HECHA LA LÓGICA
        * SETEAR EL ID DE entrenador AL ID puesto en la BD.
        */
    }

    @Override
    public Entrenador buscarPorId(long id) throws Exception {
        return null;
    }

    @Override
    public void actualizar(Entrenador entrenador) throws Exception {

    }

    @Override
    public void borrarPorId(long id) throws Exception {

    }
}
