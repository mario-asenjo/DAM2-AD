package _6_excepciones;

public class StringNoValidoException extends RuntimeException {
    public StringNoValidoException(String message) {
        super(message);
    }
}
