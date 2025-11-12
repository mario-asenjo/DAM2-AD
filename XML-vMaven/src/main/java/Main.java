import modelo.Factura;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import servicio.GestorXMLFacturas;
import vista.Consola;
import vista.Escaner;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String ruta = Escaner.pedirString("Introduce el nombre del fichero que se ubica en resources: ");
        GestorXMLFacturas gestorXML = new GestorXMLFacturas(ruta);
        List<Factura> listaFacturas = new ArrayList<>();

        try {
            listaFacturas = gestorXML.listar();
        } catch (FactoryConfigurationError | ParserConfigurationException | IOException | SAXException | IllegalArgumentException | DOMException | UnsupportedOperationException | ClassCastException e) {
            Consola.mostrarExcepcion(e);
        }
        if (!listaFacturas.isEmpty()) {
            Consola.mostrarArrayListObj(Collections.singletonList(listaFacturas));
        }
    }
}
