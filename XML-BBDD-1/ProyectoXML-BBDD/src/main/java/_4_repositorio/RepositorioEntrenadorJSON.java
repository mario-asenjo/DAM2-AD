package _4_repositorio;

// Importación de la clase Gson, utilizada para convertir objetos Java en JSON y viceversa.
// REFERENCIA: https://github.com/google/gson
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

    // Ruta del archivo de entrada JSON en disco.
    private final String ficheroEntrada;
    // Ruta del archivo de salida JSON en disco.
    private final String ficheroSalida;

    // Instancia de Gson utilizada para serializar/deserializar.
    // Serializar significa convertir un objeto en memoria (Java, Python, etc.) en un formato que pueda ser almacenado o transmitido.
    // Deserializar es el proceso inverso: convertir datos almacenados o recibidos en memoria en un objeto usable por el programa.
    private final Gson gson;

    // Tipo genérico que representa "List<Pelicula>" para poder deserializarlo correctamente.
    private final Type tipoListaPeliculas;

    // Constructor: recibe el nombre del fichero y lo crea si no existe.
    public RepositorioEntrenadorJSON(String ficheroEntrada, String ficheroSalida) {
        this.ficheroEntrada = ficheroEntrada;
        this.ficheroSalida = ficheroSalida;

        // Configuración de Gson para que el archivo JSON sea legible (con saltos de línea y sangría).
        // new Gson() funcionaría, pero produciría salida sin formato.
        // Es interesante valorar esta función para archivos XML
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        // TypeToken captura el tipo real de List<Pelicula> evitando el borrado de tipos genéricos.
        this.tipoListaPeliculas = new TypeToken<List<Entrenador>>(){}.getType();
    }

    // Guarda una nueva película en el archivo JSON.
    public void guardar(Entrenador entrenador) throws Exception {
        // Si el archivo JSON no existe, se crea uno vacío con una lista de películas vacía.
        File f = new File(ficheroSalida);
        List<Entrenador> lista;
        if (!f.exists()) {
            lista = new ArrayList<>();
        } else {
            // Primero recuperamos la lista actual del archivo.
            lista = listar();
        }

        // Agregamos la nueva película a la lista.
        lista.add(entrenador);

        // Guardamos la lista completa en el archivo.
        guardarLista(lista);
    }

    // Devuelve todas las películas almacenadas en el archivo JSON.
    public List<Entrenador> listar() throws Exception {
        Reader reader;
        List<Entrenador> lista;

        try {
            reader = new FileReader(ficheroEntrada);
            // Deserializa el contenido del archivo a List<Pelicula>.
            lista = gson.fromJson(reader, tipoListaPeliculas);

            if (lista == null) {
                throw new Exception("La lista de lectura es nula.");
            }
            return lista;
        } catch (IOException e) {
            throw new Exception("Excepción durante la lectura del JSON.");
        }
    }

    // Busca un entrenador por Id.
    public Entrenador buscarPorId(long id) throws Exception {
        List<Entrenador> lista = listar();
        for (Entrenador entrenador : lista) {
            if (entrenador.getId() == id) {
                return entrenador;
            }
        }
        throw new Exception("No se ha encontrado el entrenador con id " + id);
    }

    // Actualiza una película existente, buscándola por su ID.
    public void actualizar(Entrenador entrenador) throws Exception {
        // Cogemos la lista para modificar.
        List<Entrenador> lista = listar();

        // Modificamos buscando por id.
        lista.set(lista.indexOf(buscarPorId(entrenador.getId())), entrenador);

        // Guardamos la lista completa nuevamente.
        guardarLista(lista);
    }

    // Elimina una película del archivo JSON según su ID.
    public void borrarPorId(long id) throws Exception {
        List<Entrenador> lista = listar();

        // Muy útil!! removeIf elimina elementos que cumplan la condición del predicado.
        lista.removeIf(p -> p.getId() == id);

        guardarLista(lista);
    }

    // Método privado que sobrescribe el archivo JSON con la lista proporcionada.
    private void guardarLista(List<Entrenador> lista) throws Exception {
        Writer writer;

        try {
            writer = new FileWriter(ficheroSalida);

            // Serializa la lista a JSON y lo escribe en el archivo.
            gson.toJson(lista, writer);

            // Lo que falte en el buffer lo limpiamos y después cerramos el escritor.
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new Exception("Excepción durante la escritura del JSON.");
        }
    }
}