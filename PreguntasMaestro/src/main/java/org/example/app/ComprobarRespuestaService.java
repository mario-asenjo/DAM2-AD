package org.example.app;

import org.example.domain.ports.PreguntaRepository;

import java.sql.SQLException;

public class ComprobarRespuestaService {
    private final PreguntaRepository repo;

    public ComprobarRespuestaService(PreguntaRepository repo) {
        this.repo = repo;
    }

    public boolean esCorrecta(Long idPregunta, char opcion) throws SQLException {
        return repo.isCorrect(idPregunta, Character.toUpperCase(opcion));
    }
}
