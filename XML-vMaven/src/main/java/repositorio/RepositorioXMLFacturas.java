package repositorio;

import excepciones.LecturaEscrituraException;
import modelo.Factura;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RepositorioXMLFacturas {
    private final File fichero;

    public RepositorioXMLFacturas(String rutaFichero) throws LecturaEscrituraException, TransformerException, ParserConfigurationException {
        this.fichero = new File(rutaFichero);
        if (Files.notExists(Path.of(rutaFichero))) {
            // DocumentBuilderFactory: fábrica para crear DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // DocumentBuilder: parser para crear un Document (XML en memoria)
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Crear documento XML vacío
            Document doc = builder.newDocument();
            // Crear nodo raíz <peliculas>
            Element root = doc.createElement("peliculas");
            doc.appendChild(root);

            // Guardar el documento en disco
            guardarDocumentoFacturas(doc, this.fichero);
        }
    }

    private void guardarDocumentoFacturas(Document doc, File f) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Configuración para XML legible
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc), new StreamResult(f));
    }

    public void guardarFactura(Factura factura) throws FactoryConfigurationError, ParserConfigurationException, IOException, SAXException,
            IllegalArgumentException, DOMException, UnsupportedOperationException, ClassCastException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // parsea el fichero XML existente
        Document doc = builder.parse(fichero);

        Element root = doc.getDocumentElement();

        // Crear nodo <pelicula>
        Element facturaNode = doc.createElement("facturas");

        Element idNode = doc.createElement("id");
        idNode.appendChild(doc.createTextNode(String.valueOf(factura.getId())));
        facturaNode.appendChild(idNode);

        Element cifNode = doc.createElement("cif");
        cifNode.appendChild(doc.createTextNode(factura.getCif()));
        facturaNode.appendChild(cifNode);

        for (String item : factura.getItems()) {
            Element itemNode = doc.createElement("item");
            itemNode.appendChild(doc.createTextNode(item));
            facturaNode.appendChild(itemNode);
        }

        Element totalNode = doc.createElement("total");
        totalNode.appendChild(doc.createTextNode(factura.getTotal()));
        facturaNode.appendChild(totalNode);

        root.appendChild(facturaNode);

        // Guardar XML actualizado con buena indentación
        guardarDocumentoFacturas(doc, fichero);
    }

    public List<Factura> listar() throws FactoryConfigurationError, ParserConfigurationException, IOException, SAXException,
            IllegalArgumentException, DOMException, UnsupportedOperationException, ClassCastException {
        List<Factura> lista = new ArrayList<>();
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document doc;
        NodeList facturas;

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

                lista.add(new Factura(id, cif, items, Double.parseDouble(total.substring(0, total.length() - 1)), total.charAt(total.length() - 1)));
            }
        }
        return lista;
    }

    public void actualizarFactura(Factura factura) throws FactoryConfigurationError, IOException, SAXException,
            IllegalArgumentException, DOMException, UnsupportedOperationException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fichero);

        NodeList facturasNode = doc.getElementsByTagName("facturas");

        for (int i = 0; i < facturasNode.getLength(); i++) {
            Node node = facturasNode.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                String idActual = e.getElementsByTagName("id").item(0).getTextContent();
                if (idActual.equalsIgnoreCase(factura.getId())) {
                    e.getElementsByTagName("cif").item(0).setTextContent(factura.getCif());
                    NodeList itemsList = e.getElementsByTagName("items");
                    for (int j = 0; j < itemsList.getLength(); j++) {
                        e.getElementsByTagName("item").item(0).setTextContent(factura.getItems().get(j));
                    }
                    e.getElementsByTagName("total").item(0).setTextContent(factura.getTotal());
                    break;
                }
            }
        }

        guardarDocumentoFacturas(doc, fichero);

    }
}
