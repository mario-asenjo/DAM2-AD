package servicio;

import excepciones.LecturaEscrituraException;
import modelo.Opcion;
import modelo.Pregunta;
import repositorio.Fichero;

import java.util.ArrayList;
import java.util.List;

public class ServicioFicheroPreguntas {
    private final Fichero repo;

    public ServicioFicheroPreguntas(String ruta) {
        this.repo = new Fichero(ruta);
    }

    public List<Pregunta> leer() throws LecturaEscrituraException {
        List<Pregunta> retorno = new ArrayList<>();
        List<String> lectura = repo.leerFichero();
        String enunciado;
        long id;
        int idx_p;
        List<Opcion> opcionesSinMarcar;
        List<Opcion> opcionesMarcadas;
        char correcta;

        for (int i = 0; i < lectura.size() && i + 5 < lectura.size(); i++) {
            idx_p = lectura.get(i).indexOf(".");
            id = Long.parseLong(lectura.get(i).substring(0, idx_p));
            enunciado = lectura.get(i).substring(idx_p + 2);
            i++;

            opcionesSinMarcar = new ArrayList<>(4);
            for (int j = 0; j < 4; j++) {
                opcionesSinMarcar.add(new Opcion(null, lectura.get(i).substring(3), lectura.get(i).charAt(0), false));
                i++;
            }

            correcta = lectura.get(i).charAt(lectura.get(i).length() - 1);
            i++;
            opcionesMarcadas = new ArrayList<>(4);
            for (Opcion op : opcionesSinMarcar) {
                opcionesMarcadas.add(new Opcion(op.getId(), op.getTexto(), op.getOpcion(), op.getOpcion() == correcta));

            }

            retorno.add(new Pregunta(id, enunciado, opcionesMarcadas));
        }
        return retorno;
    }
}
