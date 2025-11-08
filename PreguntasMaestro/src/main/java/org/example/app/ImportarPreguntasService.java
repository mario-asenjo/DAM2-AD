package org.example.app;

import org.example.domain.model.Pregunta;
import org.example.domain.ports.PreguntaImporter;
import org.example.domain.ports.PreguntaRepository;

import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

public class ImportarPreguntasService {
    private final PreguntaRepository repo;
    private final PreguntaImporter importer;

    public ImportarPreguntasService(PreguntaRepository repo, PreguntaImporter importer) {
        this.repo = repo;
        this.importer = importer;
    }
    
    public record Resultado(int preguntasInsertadas, int opcionesInsertadas) {}

    public Resultado importar(Reader lector) throws SQLException {
        List<Pregunta> preguntas = importer.parse(lector);
        repo.truncate();
        var result = repo.saveAll(preguntas);
        return new Resultado(result.preguntasInsertadas(), result.opcionesInsertadas());
    }
}
