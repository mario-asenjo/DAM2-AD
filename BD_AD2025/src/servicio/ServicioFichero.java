package servicio;

import excepciones.LecturaEscrituraException;
import modelo.Incidencia;
import repositorio.Fichero;
import modelo.ListaIncidencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioFichero {
    private final Fichero repo;
    private ListaIncidencia    listaIncidencias;

    public ServicioFichero(String ruta) {
        this.repo = new Fichero(ruta);
        this.listaIncidencias = new ListaIncidencia(leer());
    }

    public void guardar(String usuario, String exceptionMessage) throws LecturaEscrituraException {
        Incidencia incidencia = new Incidencia(LocalDateTime.now(), exceptionMessage, usuario);
        if (incidencia != null) {
            repo.escribirLinea(incidencia.toFileString());
        } else {
            throw new LecturaEscrituraException("No se pudo crear el objeto Incidencia correctamente.");
        }
    }

    public void guardar(Incidencia incidencia) throws LecturaEscrituraException {
        System.out.println("Metodo guardar Objeto en ServicioFichero.");
    }

    public List<Incidencia> leer() throws LecturaEscrituraException {
        List<Incidencia> listaRetorno;
        String[] datos;
        List<String> lectura;
        Incidencia incidencia;

        listaRetorno = new ArrayList<Incidencia>();
        lectura = repo.leerFichero();
        for (String x : lectura) {
            datos = x.split(";");
            incidencia = new Incidencia(LocalDateTime.parse(String.join("T", datos[0], datos[1])), datos[2], datos[3]);
            if (incidencia != null) {
                listaRetorno.add(incidencia);
            }
        }
        return (listaRetorno);
    }
}
