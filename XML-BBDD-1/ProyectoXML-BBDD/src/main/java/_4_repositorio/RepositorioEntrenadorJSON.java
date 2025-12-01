package _4_repositorio;

import _6_excepciones.EntidadNoEncontradaException;
import _6_excepciones.RepositorioException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public void guardar(Entrenador entrenador) throws RepositorioException {
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
    public List<Entrenador> listar() throws RepositorioException {
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
            throw new RepositorioException("Excepción durante la lectura del JSON.");
        }
    }

    @Override
    public Entrenador buscarPorId(long id) throws RepositorioException, EntidadNoEncontradaException {
        List<Entrenador> lista = listar();
        boolean encontrado = false;
        Entrenador found = null;
        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getId() == id) {
                found = lista.get(i);
                encontrado = true;
            }
        }
        if (!encontrado) {
            throw new EntidadNoEncontradaException("No se ha encontrado entrenador con este ID.");
        }
        return found;
    }

    @Override
    public Entrenador buscarPorNombre(String nombre) throws RepositorioException, EntidadNoEncontradaException {
        List<Entrenador> lista = listar();
        boolean encontrado = false;
        Entrenador found = null;

        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getNombre().equalsIgnoreCase(nombre)) {
                found = lista.get(i);
                encontrado = true;
            }
        }
        if (!encontrado) {
            throw new EntidadNoEncontradaException("No se ha encontrado entrenador con este nombre.");
        }
        return found;
    }

    @Override
    public void actualizar(Entrenador entrenador) throws RepositorioException, EntidadNoEncontradaException {
        List<Entrenador> lista = listar();
        boolean encontrado = false;

        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getId() == entrenador.getId()) {
                lista.set(i, entrenador);
                encontrado = true;
            }
        }
        if (!encontrado) {
            throw new EntidadNoEncontradaException("No se ha encontrado el entrenador para actualizar.");
        }
        guardarLista(lista);
    }

    @Override
    public void borrarPorId(long id) throws RepositorioException {
        List<Entrenador> lista = listar();

        lista.removeIf(p -> p.getId() == id);
        guardarLista(lista);
    }

    private void guardarLista(List<Entrenador> lista) throws RepositorioException {
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
            throw new RepositorioException("Excepción durante la escritura del JSON.");
        }
    }
}