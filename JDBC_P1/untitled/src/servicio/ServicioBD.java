package servicio;

import modelo.Opcion;
import modelo.Pregunta;
import repositorio.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioBD {
    private final BBDD repo;

    public ServicioBD() throws SQLException {
        repo = new BBDD();
    }

    public void insertarPreguntas(List<Pregunta> preguntas) throws SQLException {
        final String insertPregunta = "INSERT INTO pregunta (ID, ENUNCIADO) VALUES (?, ?);";
        final String insertOpcion = "INSERT INTO opcion (ID, TEXTO, OPCION, CORRECTA, ID_PREGUNTA) VALUES (?, ?, ?, ?, ?);";

        for (Pregunta pregunta : preguntas) {
            repo.ejecutarDML(insertPregunta, pregunta.getId(), pregunta.getEnunciado());
            for (Opcion opcion : pregunta.getOpciones()) {
                repo.ejecutarDML(insertOpcion, opcion.getId(), opcion.getTexto(), opcion.getOpcion(), opcion.isEsCorrecta(), pregunta.getId());
            }
        }
    }

    public boolean esOpcionCorrecta(Character opcion, Long id_pregunta) throws SQLException {
        String query;

        query = "SELECT CORRECTA FROM opcion WHERE OPCION LIKE ? AND ID_PREGUNTA = ?";
        return (repo.ejecutarQuery(
                query,
                resultSet -> {
                    if (!resultSet.next()) {
                        throw new SQLException("No se ha encontrado la opcion en la base de datos.");
                    }
                    return (resultSet.getBoolean("CORRECTA"));
                },
                opcion,
                id_pregunta
        ));
    }

    public void eliminarContenidoSiHubiese() throws SQLException {
        repo.ejecutarDML("DELETE FROM pregunta;");
        repo.ejecutarDML("DELETE FROM opcion;");
    }
}
