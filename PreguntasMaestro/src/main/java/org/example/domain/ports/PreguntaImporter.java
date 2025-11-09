package org.example.domain.ports;

import org.example.domain.model.Pregunta;

import java.io.Reader;
import java.util.List;

public interface PreguntaImporter {
    /**
     * Parsea texto plano a lista de preguntas.
     * @param input
     * @return
     */
    List<Pregunta> parse();
}
