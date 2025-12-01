package _6_excepciones;

public class ApplicationException extends Exception {
    public ApplicationException(String message) {
        super(message);
    }
    public ApplicationException(String meesage, Throwable cause) {
        super(meesage, cause);
    }
}
