package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incidencia {
    LocalDateTime dateTime;
    String exceptionMessage;
    String username;
    DateTimeFormatter formatter;

    public Incidencia(LocalDateTime dateTime, String exceptionMessage, String username) {
        this.dateTime = dateTime;
        this.exceptionMessage= exceptionMessage;
        this.username = username;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd;HH:mm:ss");
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
        return String.format(
                "Date: %s, Err: %s, User: %s",
                this.dateTime.format(this.formatter), this.exceptionMessage, this.username
        );
    }

    public String toFileString() {
        return String.format(
                "%s;%s;%s",
                this.dateTime.format(this.formatter), this.exceptionMessage, this.username
        );
    }
}
