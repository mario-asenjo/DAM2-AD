package org.example.infra.file.errors;

public class LecturaEscrituraException extends RuntimeException {
    public LecturaEscrituraException(String message) {
        super(message);
    }
}
