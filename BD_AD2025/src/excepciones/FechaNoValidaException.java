package excepciones;

public class FechaNoValidaException extends RuntimeException {
    public FechaNoValidaException(String message) {
        super(message);
    }
}
