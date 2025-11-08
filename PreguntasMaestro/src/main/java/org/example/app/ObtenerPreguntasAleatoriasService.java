package org.example.app;

import org.example.domain.model.Pregunta;
import org.example.domain.ports.PreguntaRepository;

import java.sql.SQLException;
import java.util.List;

public class ObtenerPreguntasAleatoriasService {
    private final PreguntaRepository repo;

    public ObtenerPreguntasAleatoriasService(PreguntaRepository repo) {
        this.repo = repo;
    }

    public List<Pregunta> obtener(int count) throws SQLException {
        return repo.findRandom(count);
    }
}
