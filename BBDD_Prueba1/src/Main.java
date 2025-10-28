import modelo.Alumno;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Todo OK.");
            System.out.println(prepareSQLfromAlumno(new Alumno("mario", 22)));
            Connection connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String prepareSQLfromAlumno(Alumno alumno) {
        String stmt = String.format("INSERT INTO ALUMNOS (ID, NOMBRE, EDAD) VALUES (NULL, '%s', %d);",alumno.getNombre(), alumno.getEdad()));
        return (stmt);
    }
}
