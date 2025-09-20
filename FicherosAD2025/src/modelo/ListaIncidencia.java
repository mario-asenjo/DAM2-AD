package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaIncidencia {
    private List<Incidencia> incidencias;

    public ListaIncidencia(List<Incidencia> incidencias) {
        this.incidencias = new ArrayList<>(incidencias);
    }

    public List<Incidencia> buscarPorUsuario(String usuario) {
        List<Incidencia> listaRetorno = new ArrayList<>();
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getUsername().equalsIgnoreCase(usuario))
                listaRetorno.add(incidencia);
        }
        return (listaRetorno);
        //return incidencias.stream().filter(i -> i.getUsername().equalsIgnoreCase(usuario)).toList();
    }

    public List<Incidencia> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        List<Incidencia> listaRetorno = new ArrayList<>();
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getDateTime().isAfter(desde.atStartOfDay()) && incidencia.getDateTime().isBefore(hasta.atStartOfDay()))
                listaRetorno.add(incidencia);
        }
        return (listaRetorno);
    }

    public void añadirIncidencia(Incidencia incidencia) {
        incidencias.add(incidencia);
    }

    public List<Incidencia> obtenerTodas() {
        return incidencias;
    }
}
