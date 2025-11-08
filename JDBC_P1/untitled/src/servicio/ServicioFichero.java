package servicio;

import excepciones.LecturaEscrituraException;
import modelo.Opcion;
import modelo.Pregunta;
import repositorio.Fichero;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ServicioFichero {
    private final String ruta;
    private final Fichero repo;

    public ServicioFichero(String ruta) {
        this.ruta = ruta;
        this.repo = new Fichero(ruta);
    }

    public List<Pregunta> leer() throws LecturaEscrituraException {
        int contadorTemporal;
        int contIdPregunta;
        Pregunta pregunta;
        Opcion opcion;
        Long idPregunta;
        Long idOpcion;
        String enunciado;
        Character charOpcion;
        String textoOpcion;
        Character opcionCorrecta;
        List<String> lectura;
        List<Pregunta> retorno = new ArrayList<>();
        List<Opcion> opciones;

        lectura = repo.leerFichero();
        idOpcion = 0L;
        for (int i = 0; i < lectura.size() && i + 4 < lectura.size(); i++) {
            //Línea del enunciado
            contIdPregunta = 0;
            idPregunta = 0L;
            while (lectura.get(i).charAt(contIdPregunta) >= 48 && lectura.get(i).charAt(contIdPregunta) <= 57) {
                idPregunta = idPregunta * 10 + (lectura.get(i).charAt(contIdPregunta) - 48);
                contIdPregunta++;
            }
            enunciado = lectura.get(i).substring(3);
            i++;

            //Lectura opciones
            contadorTemporal = i + 4;
            opciones = new ArrayList<>();
            while (i < contadorTemporal) {
                charOpcion = lectura.get(i).charAt(0);
                textoOpcion = lectura.get(i).substring(3);
                opcion = new Opcion(idOpcion, textoOpcion, charOpcion, false);
                opciones.add(opcion);
                i++;
            }

            //Lectura respuesta correcta
            opcionCorrecta = lectura.get(i).charAt(lectura.get(i).length() - 1);
            for (Opcion opcionASetear : opciones) {
                if (opcionASetear.getOpcion() == opcionCorrecta) {
                    opcionASetear.setEsCorrecta(true);
                }
            }
            i++;

            //Creamos y añadimos la pregunta.
            pregunta = new Pregunta(idPregunta, enunciado, opciones);
            retorno.add(pregunta);
        }
        return (retorno);
    }
}
