package repositorio;
import excepciones.LecturaEscrituraException;
import vista.Consola;

import java.io.*;
import java.util.List;

<<<<<<< HEAD
public class Fichero {
    public String ruta;

    public Fichero(String ruta) {
        this.ruta = ruta;
    }

    public List<String> leerFichero() throws LecturaEscrituraException {
        FileReader archivo;
        BufferedReader lector;
        List<String> lineas;
        String linea;

        archivo = null;
        lector = null;
        lineas = null;
        try {
            archivo = new FileReader(this.ruta);
            lector = new BufferedReader(archivo);
            do {
                linea = lector.readLine();
                if (linea != null)
                    lineas.add(linea);
            } while (linea != null);
        } catch (FileNotFoundException e) {
            Consola.mostrarExcepcion(e);
            throw new LecturaEscrituraException("Error: No se encuentra el fichero en el sistema.");
        } catch (IOException e) {
            Consola.mostrarExcepcion(e);
            throw new LecturaEscrituraException("Error: Error durante la lectura en el fichero.");
        } catch (Exception e) {
            Consola.mostrarExcepcion(e);
            throw new LecturaEscrituraException("Error: Error inesperado durante la lectura del fichero.");
        } finally {
            try {
                archivo.close();
                lector.close();
            } catch (IOException e) {
                Consola.mostrarExcepcion(e);
                throw new LecturaEscrituraException("Error: Error cerrando el archivo o el lector.");
            }
        }
        return (lineas);
    }

    public void escribirLinea(String linea) throws LecturaEscrituraException {
        FileWriter archivo;
        BufferedWriter escritor;

        archivo = null;
        escritor = null;
        try {
            archivo = new FileWriter(this.ruta, true);
            escritor = new BufferedWriter(archivo);
            escritor.write(linea);
        } catch (FileNotFoundException e) {
            Consola.mostrarExcepcion(e);
            throw new LecturaEscrituraException("Error: No se encuentra el fichero en el sistema.");
        } catch (IOException e) {
            Consola.mostrarExcepcion(e);
            throw new LecturaEscrituraException("Error: Error durante la escritura en el fichero.");
        } catch (Exception e) {
            Consola.mostrarExcepcion(e);
            throw new LecturaEscrituraException("Error: Error inesperado en la escritura del fichero.");
        } finally {
            try {
                archivo.close();
                escritor.close();
            } catch (IOException e) {
                Consola.mostrarExcepcion(e);
                throw new LecturaEscrituraException("Error: Error cerrando el fichero o el lector.");
            }
        }
    }

=======
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Fichero<T> implements Repo<T> {
    private final File  archivo;
    Function<String, T> parseador;
    Function<T, String> serializador;

    public Fichero(String ruta, Function<String, T> parseador, Function<T, String> serializador) {
        this.archivo = new File(ruta);
        this.parseador = parseador;
        this.serializador = serializador;
    }

    @Override
    public void guardar(T objeto) throws IOException {
        try (BufferedWriter myBW = new BufferedWriter(new FileWriter(archivo, true))) {
            myBW.write(serializador.apply(objeto));
            myBW.newLine();
        }
    }

    @Override
    public List<T> cargar() throws IOException {
        List<T> lista;
        String  linea;

        lista = new ArrayList<>();
        if (!archivo.exists())
            return (lista);
        try (BufferedReader myBR = new BufferedReader(new FileReader(archivo))) {
            while ((linea = myBR.readLine()) != null)
                lista.add(parseador.apply(linea));
        }
        return (lista);
    }
>>>>>>> 44ac764ec9ee34501041d0ca3c22ac07871f22c0
}
