package repositorio;

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
}
