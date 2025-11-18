import modelo.Factura;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import repositorio.RepositorioXMLFacturas;
import vista.Colores;
import vista.Consola;
import vista.Escaner;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String ruta = Escaner.pedirString("Introduce el nombre del fichero que se ubica en resources: ");
        try {
            RepositorioXMLFacturas repo = new RepositorioXMLFacturas(ruta);
            List<Factura> listaFacturas;

            Consola.mostrarFraseEndl("### MOSTRANDO LISTA ACTUAL ###", Colores.VERDE);
            listaFacturas = repo.listar("factura", "id", "cif", "items", "item", "total");
            if (!listaFacturas.isEmpty()) {
                Consola.mostrarArrayListObj(listaFacturas);
            }
            Consola.mostrarFraseEndl("### TERMINADO LISTADO ACTUAL ###", Colores.VERDE);

            Consola.mostrarFraseEndl("### INSERTANDO NUEVA FACTURA ###");
            Factura factura = new Factura("003/25", "1235632G", List.of("comercio", "marketing"), 10.22, '€');
            repo.guardar(factura);
            Consola.mostrarFraseEndl("### INSERTADA LA NUEVA FACTURA ###");

            Consola.mostrarFraseEndl("### MOSTRANDO LISTA ACTUAL ###", Colores.VERDE);
            listaFacturas = repo.listar("factura", "id", "cif", "items", "item", "total");
            if (!listaFacturas.isEmpty()) {
                Consola.mostrarArrayListObj(listaFacturas);
            }
            Consola.mostrarFraseEndl("### TERMINADO LISTADO ACTUAL ###", Colores.VERDE);
        } catch (FactoryConfigurationError | TransformerException | ParserConfigurationException | IOException | SAXException | IllegalArgumentException | DOMException | UnsupportedOperationException | ClassCastException e) {
            Consola.mostrarExcepcion(e);
        }
    }
}
