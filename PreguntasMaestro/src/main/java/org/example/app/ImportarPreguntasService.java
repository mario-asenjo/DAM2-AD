package org.example.app;

import org.example.domain.model.Pregunta;
import org.example.domain.ports.PreguntaImporter;
import org.example.domain.ports.PreguntaRepository;
import org.example.infra.file.errors.LecturaEscrituraException;

import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

public class ImportarPreguntasService {
    private final PreguntaRepository repo;

    public ImportarPreguntasService(PreguntaRepository repo) {
        this.repo = repo;
    }
    
    public record Resultado(int preguntasInsertadas, int opcionesInsertadas) {}

    public Resultado importar(PreguntaImporter importer) throws SQLException, LecturaEscrituraException {
        List<Pregunta> preguntas = importer.parse();
        repo.truncate();
        var result = repo.saveAll(preguntas);
        return new Resultado(result.preguntasInsertadas(), result.opcionesInsertadas());
    }
}
