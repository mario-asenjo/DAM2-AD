package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class ListaPreguntas {
    private List<Pregunta> listaDePreguntas;

    public ListaPreguntas() {
        this.listaDePreguntas = new ArrayList<>();
    }

    public void setListaDePreguntas(List<Pregunta> listaPreguntas) {
        this.listaDePreguntas = listaPreguntas;
    }

    public List<Pregunta> getListaDePreguntas() {
        return this.listaDePreguntas;
    }
}
