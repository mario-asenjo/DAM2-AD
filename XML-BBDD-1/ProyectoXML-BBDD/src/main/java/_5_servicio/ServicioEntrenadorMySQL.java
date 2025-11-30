package _5_servicio;

import _4_repositorio.RepositorioEntrenador;
import _4_repositorio.RepositorioEntrenadorMySQL;

public class ServicioEntrenadorMySQL implements ServicioEntrenador {
    private final RepositorioEntrenador repo;

    public ServicioEntrenadorMySQL() {
        this.repo = new RepositorioEntrenadorMySQL();
    }
}
