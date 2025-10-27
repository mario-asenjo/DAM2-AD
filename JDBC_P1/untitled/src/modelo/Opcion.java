package modelo;

public class Opcion {
    private final Long id;
    private final String texto;
    private final Character opcion;
    private boolean esCorrecta;

    public Opcion(Long id, String texto, Character opcion, boolean esCorrecta) {
        this.id = id;
        this.texto = texto;
        this.opcion = opcion;
        this.esCorrecta = esCorrecta;
    }

    public Long getId() {
        return this.id;
    }

    public String getTexto() {
        return this.texto;
    }

    public Character getOpcion() {
        return this.opcion;
    }

    public boolean isEsCorrecta() {
        return this.esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    @Override
    public String toString() {
        return String.format("[ %c ] %s", this.opcion, this.texto);
    }
}
