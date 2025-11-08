package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.Incidencia;

public class ListaIncidencia {
    private List<Incidencia> incidencias;

    public ListaIncidencia(List<Incidencia> incidencias) {
        this.incidencias = new ArrayList<>(incidencias);
    }

    public List<String> buscarPorUsuario(String usuario) {
        List<String> listaRetorno;

        listaRetorno = new ArrayList<>();
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getUsername().equalsIgnoreCase(usuario))
                listaRetorno.add(incidencia.toString());
        }
        return (listaRetorno);
        //return incidencias.stream().filter(i -> i.getUsername().equalsIgnoreCase(usuario)).toList();
    }

    public List<String> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        List<String> listaRetorno;

        listaRetorno = new ArrayList<>();
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getDateTime().isAfter(desde.atStartOfDay())
                    && incidencia.getDateTime().isBefore(hasta.plusDays(1).atStartOfDay()))
                listaRetorno.add(incidencia.toString());
        }
        return (listaRetorno);
    }

    public void anadirIncidencia(String mensaje, String usuario) {
        incidencias.add(new Incidencia(LocalDateTime.now(), mensaje, usuario));
    }

    public void reinsertarIncidencias(List<Incidencia> listaNueva) {
        incidencias = listaNueva;
    }

    public List<Incidencia> obtenerTodas() {
        return (incidencias);
    }
}
