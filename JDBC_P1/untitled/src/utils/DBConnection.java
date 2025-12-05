package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_preguntas_entrega?useSSL=false&serverTimezone=UTC";
    private static final String USER = "jdbcpreguntas"; //poner un usuario del servidor real que no sea root
    private static final String PASSWORD = "12345"; //en mi casa tengo contraseña QUITAR SI CONFIG DEFAULT
    private static Connection connection = null;

    private DBConnection(){}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URL, USER, PASSWORD); //pilla una conection de driver manager
        }
        return connection;
    }
}
