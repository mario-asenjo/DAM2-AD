package org.example.domain.model;

import java.util.List;

public class Pregunta {
    private final Long id;
    private final String enunciado;
    private final List<Opcion> opciones;

    public Pregunta(Long id, String enunciado, List<Opcion> opciones) {
        this.id = id;
        this.enunciado = enunciado;
        this.opciones = List.copyOf(opciones);
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
}
