package excepciones;

public class StringNoValidoException extends RuntimeException {
    public StringNoValidoException(String message) {
        super(message);
    }
}
