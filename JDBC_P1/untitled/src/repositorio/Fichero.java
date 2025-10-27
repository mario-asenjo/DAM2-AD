package repositorio;
import excepciones.LecturaEscrituraException;
import vista.Consola;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        lineas = new ArrayList<>();
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
                if (archivo != null) {
                    archivo.close();
                }
                if (lector != null) {
                    lector.close();
                }
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
            throw new LecturaEscrituraException("Error: No se encuentra el fichero en el sistema.");
        } catch (IOException e) {
            throw new LecturaEscrituraException("Error: Error durante la escritura en el fichero.");
        } catch (Exception e) {
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

}
