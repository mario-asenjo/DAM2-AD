package modelo;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incidencia {
    private LocalDateTime   dateTime;
    private String          exceptionMessage;
    private String          username;
    private static final    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd;HH:mm:ss");

    public Incidencia(LocalDateTime dateTime, String exceptionMessage, String username) {
        this.dateTime = dateTime;
        this.exceptionMessage = exceptionMessage;
        this.username = username;
    }

    public LocalDateTime getDateTime() {
        return (this.dateTime);
    }

    public String getExceptionMessage() {
        return (this.exceptionMessage);
    }

    public String getUsername() {
        return (this.username);
    }

    public void setDateTime(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }

    public void setExceptionMessage(String newExceptionMessage) {
        this.exceptionMessage = newExceptionMessage;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    @Override
    public String toString() {
        return (String.format(
                "Date: %s, Err: %s, User: %s",
                this.dateTime.format(formatter), this.exceptionMessage, this.username
        ));
    }

    public String toFileString() {
        return (this.dateTime.format(formatter) + ";" + this.exceptionMessage + ";" + this.username);
    }

    public static Incidencia fromString(String linea) {
        String[]    campos;
        Incidencia  incidencia;

        campos = linea.split(";");
        incidencia = new Incidencia(LocalDateTime.parse(String.join("T", campos[0], campos[1])), campos[2], campos[3]);
        return (incidencia);
    }
}
