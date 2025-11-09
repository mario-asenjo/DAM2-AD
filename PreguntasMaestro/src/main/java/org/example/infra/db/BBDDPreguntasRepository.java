package org.example.infra.db;

import org.example.domain.model.Opcion;
import org.example.domain.model.Pregunta;
import org.example.domain.ports.PreguntaRepository;

import java.sql.SQLException;
import java.util.List;

public class BBDDPreguntasRepository implements PreguntaRepository {
    private final BBDD repo;

    public BBDDPreguntasRepository(BBDD repo) {
        this.repo = repo;
    }

    @Override
    public void truncate() throws SQLException {
        repo.inTransaction( () -> {
                    repo.ejecutarDML("DELETE FROM OPCION");
                    repo.ejecutarDML("DELETE FROM PREGUNTA");
                    return null;
                }
        );
    }

    @Override
    public Resultado saveAll(List<Pregunta> preguntas) throws SQLException {
        final String insertPregunta = "INSERT INTO PREGUNTA (ID, ENUNCIADO) VALUES (?, ?)";
        final String insertOpcion = "INSERT INTO OPCION (ID, TEXTO, OPCION, CORRECTA, ID_PREGUNTA) VALUES (?, ?, ?, ?)";

        return repo.inTransaction(() -> {
            int preguntasCount = 0;
            int opcionesCount = 0;

            for (Pregunta p : preguntas) {
                preguntasCount += repo.ejecutarDML(insertPregunta, p.getId(), p.getEnunciado());
                for (Opcion o : p.getOpciones()) {
                    opcionesCount += repo.ejecutarDML(insertOpcion, null, o.getTexto(), String.valueOf(o.getOpcion()), o.isCorrecta(), p.getId());
                }
            }
            return new Resultado(preguntasCount, opcionesCount);
        });
    }
}
