package _6_excepciones;

public class RepositorioException extends ApplicationException {
    public RepositorioException(String message) {
        super(message);
    }

    public RepositorioException(String message, Throwable cause) {
        super(message, cause);
    }
}
