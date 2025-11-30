package _5_servicio;

import _4_repositorio.RepositorioEntrenador;
import _4_repositorio.RepositorioEntrenadorJSON;

public class ServicioEntrenadorJSON implements ServicioEntrenador {
    private final RepositorioEntrenador repo;

    public ServicioEntrenadorJSON(String ficheroEntrada, String ficheroSalida) {
        this.repo = new RepositorioEntrenadorJSON(ficheroEntrada, ficheroSalida);
    }
}
