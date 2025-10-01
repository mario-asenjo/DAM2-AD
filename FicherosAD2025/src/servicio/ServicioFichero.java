package servicio;

import excepciones.LecturaEscrituraException;
import modelo.Incidencia;
import repositorio.Fichero;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioFichero {
    Fichero repo;

    public ServicioFichero(String ruta) {
        this.repo = new Fichero(ruta);
    }

    public void guardar(String dato) throws LecturaEscrituraException {
        repo.escribirLinea(dato);
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
            if (incidencia != null)
                listaRetorno.add(incidencia);
        }
        return (listaRetorno);
    }
}
