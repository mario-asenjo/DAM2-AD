package org.example.domain.ports;

public interface ImporterFactory {
    PreguntaImporter fromPath(String path);
}
