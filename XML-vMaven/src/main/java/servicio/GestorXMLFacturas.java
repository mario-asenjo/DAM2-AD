package servicio;

import modelo.Factura;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorXMLFacturas {

    File fichero;

    public GestorXMLFacturas(String ruta) throws NullPointerException {
        this.fichero = new File(ruta);
    }

    public List<Factura> listar()
                throws FactoryConfigurationError, ParserConfigurationException, IOException,
                SAXException, IllegalArgumentException, DOMException, UnsupportedOperationException, ClassCastException {
        List<Factura> lista = new ArrayList<>();
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document doc;
        NodeList facturas;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(fichero);

            facturas = doc.getElementsByTagName("facturas");

            for (int i = 0; i < facturas.getLength(); i++) {
                Node node = facturas.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;

                    String id = e.getAttributes().getNamedItem("factura").getTextContent();
                    String cif = e.getElementsByTagName("cif").item(0).getTextContent();

                    NodeList itemsList = e.getElementsByTagName("items");
                    List<String> items = new ArrayList<>();
                    for (int j = 0; j < itemsList.getLength(); j++) {
                        Node itemNode = itemsList.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element item = (Element) itemNode;
                            String itemName = item.getElementsByTagName("item").item(0).getTextContent();
                            items.add(itemName);
                        }
                    }

                    String total = e.getElementsByTagName("total").item(0).getTextContent();
                    double moneda = Double.parseDouble(total.substring(0, total.length() - 1));
                    char currencia = total.charAt(total.length() - 1);

                    lista.add(new Factura(id, cif, items, moneda, currencia));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
