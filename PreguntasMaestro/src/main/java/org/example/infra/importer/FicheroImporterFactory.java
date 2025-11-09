package org.example.infra.importer;

import org.example.domain.ports.ImporterFactory;
import org.example.domain.ports.PreguntaImporter;
import org.example.infra.file.Fichero;

public class FicheroImporterFactory implements ImporterFactory {
    private final String baseDir;

    public FicheroImporterFactory(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public PreguntaImporter fromPath(String path) {
        return new FicheroPreguntasImporter(new Fichero(baseDir + "\\" + path));
    }
}
