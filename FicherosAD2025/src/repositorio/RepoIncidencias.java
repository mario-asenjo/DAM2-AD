package repositorio;

import modelo.Incidencia;

public class RepoIncidencias extends Fichero<Incidencia> {
    public RepoIncidencias(String ruta) {
        super(ruta, linea -> Incidencia.fromString(linea), incidencia -> incidencia.toFileString());
    }
}
