package org.example.domain.ports;

import org.example.domain.model.Pregunta;

import java.sql.SQLException;
import java.util.List;

public interface PreguntaRepository {
    void truncate() throws SQLException;
    Resultado saveAll(List<Pregunta> preguntas) throws SQLException;
    public record Resultado(int preguntasInsertadas, int opcionesInsertadas) {}
    List<Pregunta> findRandom(int count) throws SQLException;
    boolean isCorrect(Long idPregunta, char opcion) throws SQLException;
}
