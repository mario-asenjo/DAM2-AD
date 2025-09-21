package controlador;

import modelo.Incidencia;
import repositorio.Fichero;
import repositorio.RepoIncidencias;
import utilidades.GeneradorVisualizadorIncidencias;

public class ControladorIncidencias {
    static boolean LOGIN = false;
    static boolean EXIT = false;

    static Fichero<Incidencia>  repo = new RepoIncidencias("Datos/incidencias.txt");

    public static void  iniciar() {
        String usuario;

        usuario = GeneradorVisualizadorIncidencias.login();
        GeneradorVisualizadorIncidencias.mainLoop(repo, usuario);
    }
}
