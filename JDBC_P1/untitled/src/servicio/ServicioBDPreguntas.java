package servicio;

import modelo.Opcion;
import modelo.Pregunta;
import repositorio.BBDD;
import utils.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioBDPreguntas {
    private final BBDD repo;

    public ServicioBDPreguntas() throws SQLException {
        repo = new BBDD(DBConnection.getConnection());
    }

    public List<Pregunta> devolverSeleccionPreguntas(int numeroPreguntas) throws SQLException {
        List<Pregunta> retorno;
        String sql;

        sql = "SELECT * FROM preguntas_desordenadas LIMIT ?";
        retorno = repo.ejecutarQuery(
                    sql,
                    resultSet -> {
                        List<Pregunta> listaRetorno = new ArrayList<>();
                        List<Opcion> opciones;
                        String enunciado;
                        Long idPregunta;
                        resultSet.next();
                        while (!resultSet.isLast()) {
                            idPregunta = resultSet.getLong("P_ID");
                            enunciado = resultSet.getString("P_ENUNCIADO");
                            opciones = new ArrayList<>();
                            for (int i = 0; i < 4; i++) {
                                opciones.add(
                                        new Opcion(resultSet.getLong("O_ID"),
                                                resultSet.getString("O_TEXTO"),
                                                resultSet.getString("O_OPCION").charAt(0),
                                                resultSet.getBoolean("O_CORRECTA"))
                                );
                                if (!resultSet.isLast())
                                    resultSet.next();
                            }
                            listaRetorno.add(
                                    new Pregunta(idPregunta, enunciado, opciones)
                            );
                        }
                        return (listaRetorno);
                    },
                    numeroPreguntas * 4
                );
        return retorno;
    }

    public int insertarPreguntas(List<Pregunta> preguntas) throws SQLException {
        final String insertPregunta = "INSERT INTO preguntas (ID, ENUNCIADO) VALUES (?, ?);";
        final String insertOpcion = "INSERT INTO opcion (ID, TEXTO, OPCION, CORRECTA, ID_PREGUNTA) VALUES (?, ?, ?, ?, ?);";
        int preguntasInsertadas;
        int respuestasInsertadas;

        preguntasInsertadas = 0;
        respuestasInsertadas = 0;
        for (Pregunta pregunta : preguntas) {
            preguntasInsertadas += repo.ejecutarDML(insertPregunta, pregunta.getId(), pregunta.getEnunciado());
            for (Opcion opcion : pregunta.getOpciones()) {
                respuestasInsertadas += repo.ejecutarDML(insertOpcion, opcion.getId(), opcion.getTexto(), opcion.getOpcion(), opcion.isEsCorrecta(), pregunta.getId());
            }
        }
        return (preguntasInsertadas + respuestasInsertadas);
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
        repo.ejecutarDML("DELETE FROM preguntas;");
        repo.ejecutarDML("DELETE FROM opcion;");
    }

    public void cerrarServicio() throws SQLException {
        if (repo.isConnected()) {
            repo.closeConnection();
        }
    }
}
