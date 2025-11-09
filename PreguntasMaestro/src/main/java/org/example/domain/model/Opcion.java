package org.example.domain.model;

public class Opcion {
    private final Long id;       // puede venir de la BD
    private final String texto;
    private final char opcion;   // 'A'..'D'
    private final boolean correcta;

    public Opcion(Long id, String texto, char opcion, boolean correcta) {
        this.id = id;
        this.texto = texto;
        this.opcion = opcion;
        this.correcta = correcta;
        }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public char getOpcion() {
        return opcion;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    @Override
    public String toString() {
        return String.format("[ %c ] %s", this.opcion, this.texto);
    }

}
