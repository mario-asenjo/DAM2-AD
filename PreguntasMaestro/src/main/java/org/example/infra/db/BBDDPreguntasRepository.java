package org.example.infra.db;

import org.example.domain.model.Opcion;
import org.example.domain.model.Pregunta;
import org.example.domain.ports.PreguntaRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BBDDPreguntasRepository implements PreguntaRepository {
    private final BBDD repo;

    public BBDDPreguntasRepository(BBDD repo) {
        this.repo = repo;
    }

    private static class PreguntaBuilder {
        private final long id;
        private final String enunciado;
        private final List<Opcion> opciones = new ArrayList<>(4);

        private PreguntaBuilder(long id, String enunciado) {
            this.id = id;
            this.enunciado = enunciado;
        }

        private void add(Long op_id, String op_texto, char op_opcion, boolean op_correcta) {
            opciones.add(new Opcion(op_id, op_texto, op_opcion, op_correcta));
        }

        private Pregunta build() {
            return new Pregunta(id, enunciado, opciones);
        }
    }

    @Override
    public void truncate() throws SQLException {
        repo.inTransaction( (Connection conn) -> {
                    repo.ejecutarDML(conn, "DELETE FROM OPCION");
                    repo.ejecutarDML(conn, "DELETE FROM PREGUNTAS");
                    return null;
                }
        );
    }

    @Override
    public Resultado saveAll(List<Pregunta> preguntas) throws SQLException {
        final String insertPregunta = "INSERT INTO PREGUNTAS (ID, ENUNCIADO) VALUES (?, ?)";
        final String insertOpcion = "INSERT INTO OPCION (ID, TEXTO, OPCION, CORRECTA, ID_PREGUNTA) VALUES (?, ?, ?, ?, ?)";

        return repo.inTransaction((Connection conn) -> {
            int preguntasCount = 0;
            int opcionesCount = 0;

            for (Pregunta p : preguntas) {
                preguntasCount += repo.ejecutarDML(conn, insertPregunta, p.getId(), p.getEnunciado());
                for (Opcion o : p.getOpciones()) {
                    opcionesCount += repo.ejecutarDML(conn, insertOpcion, null, o.getTexto(), String.valueOf(o.getOpcion()), o.isCorrecta(), p.getId());
                }
            }
            return new Resultado(preguntasCount, opcionesCount);
        });
    }

    @Override
    public List<Pregunta> findRandom(int count) throws SQLException {
        final String SQL = """
                SELECT P_ID, P_ENUNCIADO, O_ID, O_TEXTO, O_OPCION, O_CORRECTA
                FROM preguntas_desordenadas
                LIMIT ?;
                """;
        int rows = count * 4;
        return repo.ejecutarQuery(SQL, resultSet -> {
            Map<Long, PreguntaBuilder> map = new LinkedHashMap<>();
            List<Pregunta> retorno;
            while (resultSet.next()) {
                long pid = resultSet.getLong("P_ID");
                String enunciado = resultSet.getString("P_ENUNCIADO");
                PreguntaBuilder pb = map.computeIfAbsent(pid, k -> new PreguntaBuilder(pid, enunciado));
                pb.add(
                        resultSet.getLong("O_ID"),
                        resultSet.getString("O_TEXTO"),
                        resultSet.getString("O_OPCION").charAt(0),
                        resultSet.getBoolean("O_CORRECTA")
                );
            }
            retorno = new ArrayList<>(map.size());
            retorno.addAll(map.values().stream().map(PreguntaBuilder::build).toList());
            return retorno;
        }, rows);
    }

    @Override
    public boolean isCorrect(Long idPregunta, char opcion) throws SQLException {
        final String SQL = "SELECT CORRECTA FROM OPCION WHERE ID_PREGUNTA = ? AND OPCION = ?";
        return repo.ejecutarQuery(SQL, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getBoolean("CORRECTA");
            }
            throw new SQLException("Opcion no encontrada en la bd.");
        }, idPregunta, String.valueOf(Character.toUpperCase(opcion)));
    }
}
