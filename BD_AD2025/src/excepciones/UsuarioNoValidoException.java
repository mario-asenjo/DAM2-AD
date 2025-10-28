package excepciones;

public class UsuarioNoValidoException extends RuntimeException {
    public UsuarioNoValidoException(String message) {
        super(message);
    }
}
