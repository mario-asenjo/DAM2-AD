package org.example.infra.file;

import org.example.domain.model.Opcion;
import org.example.domain.model.Pregunta;
import org.example.domain.ports.PreguntaImporter;

import java.util.ArrayList;
import java.util.List;

/**
 * Importador ultra simple: asume archivo correcto.
 * Bloques de 6 líneas:
 *  0: "N. ENUNCIADO..."
 *  1: "A. TEXTO..."
 *  2: "B. TEXTO..."
 *  3: "C. TEXTO..."
 *  4: "D. TEXTO..."
 *  5: "RESPUESTA: X"
 */
public class FicheroPreguntasImporter implements PreguntaImporter {
    private final Fichero fichero;

    public FicheroPreguntasImporter(Fichero fichero) {
        this.fichero = fichero;
    }

    @Override
    public List<Pregunta> parse() {
        List<String> lectura = fichero.leerFichero();
        return parsearLectura(lectura);
    }

    private List<Pregunta> parsearLectura(List<String> lectura) {
        List<Pregunta> retorno = new ArrayList<>();
        String enunciado;
        Long id;
        int idx_p;
        List<Opcion> opcionesSinMarcar;
        List<Opcion> opcionesMarcadas;
        char correcta;

        for (int i = 0; i < lectura.size() && i + 5 < lectura.size(); i++) {
            idx_p = lectura.get(i).indexOf(".");
            id = Long.parseLong(lectura.get(i).substring(0, idx_p));
            enunciado = lectura.get(i).substring(idx_p + 2);

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

            while (i < lectura.size() && lectura.get(i).isBlank()) {
                i++;
            }
        }
        return retorno;
    }
}
