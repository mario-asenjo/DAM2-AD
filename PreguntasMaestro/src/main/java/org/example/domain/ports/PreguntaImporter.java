package org.example.domain.ports;

import org.example.domain.model.Pregunta;
import org.example.infra.file.errors.LecturaEscrituraException;

import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

public interface PreguntaImporter {
    /**
     * Parsea texto plano a lista de preguntas.
     * @return
     */
    List<Pregunta> parse() throws LecturaEscrituraException;
}
