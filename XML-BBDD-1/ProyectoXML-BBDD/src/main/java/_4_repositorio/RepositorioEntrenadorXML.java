package _4_repositorio;

public class RepositorioEntrenadorXML implements RepositorioEntrenador {
    private final String ficheroEntrada;
    private final String ficheroSalida;

    public RepositorioEntrenadorXML(String ficheroEntrada, String ficheroSalida) {
        this.ficheroEntrada = ficheroEntrada;
        this.ficheroSalida = ficheroSalida;
    }
}
