package _4_repositorio;

import _1_vista.Escaner;
import com.google.gson.Gson;

// Importamos GsonBuilder, que permite configurar el comportamiento de Gson (como formato legible).
import com.google.gson.GsonBuilder;

// TypeToken permite capturar tipos genéricos en tiempo de ejecución (necesario para listas).
// Java solo “sabe” que es una List, no que contiene Pelicula.
// Esto provoca problemas cuando Gson intenta deserializar JSON
// REFERENCIA: https://www.javadoc.io/doc/com.google.code.gson/gson/2.6.2/com/google/gson/reflect/TypeToken.html
import com.google.gson.reflect.TypeToken;

import _3_modelo.Entrenador;

import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.lang.reflect.Type; // Permite manipular tipos genéricos.
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntrenadorJSON implements RepositorioEntrenador {

    private final String ficheroEntrada;
    private final String ficheroSalida;

    private final Gson gson;

    private final Type tipoListaEntrenadores;

    public RepositorioEntrenadorJSON(String ficheroEntrada, String ficheroSalida) {
        this.ficheroEntrada = ficheroEntrada;
        this.ficheroSalida = ficheroSalida;

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.tipoListaEntrenadores = new TypeToken<List<Entrenador>>(){}.getType();
    }

    @Override
    public void guardar(Entrenador entrenador) throws Exception {
        File f = new File(ficheroSalida);
        List<Entrenador> lista;

        if (!f.exists()) {
            lista = new ArrayList<>();
        } else {
            lista = listar();
        }
        lista.add(entrenador);
        guardarLista(lista);
    }

    @Override
    public List<Entrenador> listar() throws Exception {
        File fSalida = new File(ficheroSalida);
        File fEntrada = new File(ficheroEntrada);
        Reader reader = null;
        List<Entrenador> lista = null;

        try {
            if (fSalida.exists()) {
                reader = new FileReader(fSalida);
            } else {
              if (fEntrada.exists()) {
                  reader = new FileReader(fEntrada);
              } else {
                  return new ArrayList<Entrenador>();
              }
            }
            lista = gson.fromJson(reader, tipoListaEntrenadores);
            if (lista == null) {
                lista = new ArrayList<Entrenador>();
            }
            reader.close();
            return lista;
        } catch (IOException e) {
            if (reader != null) {
                try {
                    reader.close();
            } catch (IOException _) { } // Truco ignorar excepción, nunca se va a dar. Hay que añadirla por el compilador.
            }
            throw new Exception("Excepción durante la lectura del JSON.");
        }
    }

    @Override
    public Entrenador buscarPorId(long id) throws Exception {
        List<Entrenador> lista = listar();
        boolean encontrado = false;
        Entrenador found = null;
        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getId() == id) {
                found = lista.get(i);
                encontrado = true;
            }
        }
        return found;
    }

    @Override
    public Entrenador buscarPorNombre(String nombre) throws Exception {
        List<Entrenador> lista = listar();
        boolean encontrado = false;
        Entrenador found = null;

        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getNombre().equalsIgnoreCase(nombre)) {
                found = lista.get(i);
                encontrado = true;
            }
        }
        return found;
    }

    @Override
    public void actualizar(Entrenador entrenador) throws Exception {
        List<Entrenador> lista = listar();
        boolean encontrado = false;

        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getId() == entrenador.getId()) {
                lista.set(i, entrenador);
                encontrado = true;
            }
        }
        if (!encontrado) {
            throw new Exception("No se ha encontrado el entrenador para actualizar.");
        }
        guardarLista(lista);
    }

    @Override
    public void borrarPorId(long id) throws Exception {
        List<Entrenador> lista = listar();

        lista.removeIf(p -> p.getId() == id);
        guardarLista(lista);
    }

    private void guardarLista(List<Entrenador> lista) throws Exception {
        Writer writer = null;

        try {
            writer = new FileWriter(ficheroSalida);
            gson.toJson(lista, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException _) { }
            }
            throw new Exception("Excepción durante la escritura del JSON.");
        }
    }
}