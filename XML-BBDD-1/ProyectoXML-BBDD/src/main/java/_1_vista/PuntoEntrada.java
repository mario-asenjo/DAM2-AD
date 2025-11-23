package _1_vista;

import _3_modelo.Entrenador;
import _3_modelo.Pokedex;
import _3_modelo.Pokemon;
import _4_repositorio.*;

import java.util.List;

public class PuntoEntrada {
    private final RepositorioEntrenador repo;

    public PuntoEntrada() {
        Consola.mostrarMenu(List.of("Elige el repositorio a utilizar:", "Repo XML.", "Repo MySQL.", "Repo JSON.", "Repo MongoDB."));
        int opcionRepo = Escaner.pedirEntero("Introduce tu opcion: ");
        switch (opcionRepo) {
            // case 1 -> repo = new RepositorioEntrenadorXML();
            // case 2 -> repo = new RepositorioEntrenadorMySQL();
            case 3 -> repo = new RepositorioEntrenadorJSON("datos/json_salida.json", "datos/json_salida.json");
            // case 4 -> repo = new RepositorioEntrenadorMongoDB();
            default -> repo = null;
        }
    }

    public void iniciar() {
        Entrenador prueba = new Entrenador(1,"Mario", "HojaSuave", 23,
                                new Pokedex(20,
                                    List.of(new Pokemon(1, "Pikachu", "Rayo", 22))));
        try {
            repo.guardar(prueba);
            Consola.mostrarFraseEndl(repo.buscarPorId(1).toString(), Colores.VERDE);
        } catch (Exception e) {
            Consola.mostrarExcepcion(e);
        }
    }
}
