package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class ListaPreguntas {
    private List<Pregunta> listaPreguntas;
    private List<Pregunta> listaSeleccionada;

    public ListaPreguntas(List<Pregunta> preguntas) {
        this.listaPreguntas = new ArrayList<>(preguntas);
        this.listaSeleccionada = new ArrayList<>();
    }

    public void seleccionarPreguntas(int preguntasADevolver) {
        Collections.shuffle(this.listaPreguntas, RandomGenerator.getDefault());
        for (int i = 0; i < preguntasADevolver; i++) {
            this.listaSeleccionada.add(listaPreguntas.get(i));
        }
    }

    public List<Pregunta> obtenerTodas() {
        return this.listaPreguntas;
    }

    public List<Pregunta> obtenerSeleccionadas() {
        return this.listaSeleccionada;
    }
}
