package modelo;

import java.util.List;

public class Pregunta {
    private final Long id;
    private final String enunciado;
    private final List<Opcion> opciones;

    public Pregunta(Long id, String enunciado, List<Opcion> opciones) {
        this.id = id;
        this.enunciado = enunciado;
        this.opciones = opciones;
    }

    public Long getId() {
        return id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        mySB.append(this.id).append(". ").append(this.enunciado).append("\n");
        for (Opcion opcion : this.opciones) {
            mySB.append(opcion.toString()).append("\n");
        }
        return (mySB.toString());
    }
}
