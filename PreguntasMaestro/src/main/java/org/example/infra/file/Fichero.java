package org.example.infra.file;

import org.example.infra.file.errors.LecturaEscrituraException;

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

        lineas = new ArrayList<>();
        try {
            archivo = new FileReader(this.ruta);
            lector = new BufferedReader(archivo);
            do {
                linea = lector.readLine();
                if (linea != null)
                    lineas.add(linea);
            } while (linea != null);
            lector.close();
            archivo.close();
        } catch (FileNotFoundException e) {
            throw new LecturaEscrituraException("Error: No se encuentra el fichero en el sistema.");
        } catch (IOException e) {
            throw new LecturaEscrituraException("Error: Error durante la lectura en el fichero.");
        } catch (Exception e) {
            throw new LecturaEscrituraException("Error: Error inesperado durante la lectura del fichero.");
        }
        return (lineas);
    }

    public void escribirLinea(String linea) throws LecturaEscrituraException {
        FileWriter archivo;
        BufferedWriter escritor;

        try {
            archivo = new FileWriter(this.ruta, true);
            escritor = new BufferedWriter(archivo);
            escritor.write(linea);
            escritor.flush();
            escritor.close();
            archivo.close();
        } catch (FileNotFoundException e) {
            throw new LecturaEscrituraException("Error: No se encuentra el fichero en el sistema.");
        } catch (IOException e) {
            throw new LecturaEscrituraException("Error: Error durante la escritura en el fichero.");
        } catch (Exception e) {
            throw new LecturaEscrituraException("Error: Error inesperado en la escritura del fichero.");
        }
    }
}
