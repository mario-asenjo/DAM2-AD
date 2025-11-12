import org.example.modelo.Pelicula;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChorizoCarlos {

    private final String fichero;

    public RepositorioXML(String fichero) {
        this.fichero = fichero;

        File f = new File(fichero);
        if (!f.exists()) {
            try {
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
                guardarDocumento(doc, f);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void guardar(Pelicula p) {
        try {
            File f = new File(fichero);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // parsea el fichero XML existente
            Document doc = builder.parse(f);

            Element root = doc.getDocumentElement();

            // Crear nodo <pelicula>
            Element peliculaNode = doc.createElement("pelicula");

            Element idNode = doc.createElement("id");
            idNode.appendChild(doc.createTextNode(String.valueOf(p.getId())));
            peliculaNode.appendChild(idNode);

            Element tituloNode = doc.createElement("titulo");
            tituloNode.appendChild(doc.createTextNode(p.getTitulo()));
            peliculaNode.appendChild(tituloNode);

            Element generoNode = doc.createElement("genero");
            generoNode.appendChild(doc.createTextNode(p.getGenero()));
            peliculaNode.appendChild(generoNode);

            Element minutosNode = doc.createElement("minutos");
            minutosNode.appendChild(doc.createTextNode(String.valueOf(p.getMinutos())));
            peliculaNode.appendChild(minutosNode);

            root.appendChild(peliculaNode);

            // Guardar XML actualizado con buena indentación
            guardarDocumento(doc, f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pelicula> listar() {
        List<Pelicula> lista = new ArrayList<>();
        try {
            File f = new File(fichero);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            NodeList peliculasNode = doc.getElementsByTagName("pelicula");

            for (int i = 0; i < peliculasNode.getLength(); i++) {
                Node node = peliculasNode.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;

                    int id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                    String titulo = e.getElementsByTagName("titulo").item(0).getTextContent();
                    String genero = e.getElementsByTagName("genero").item(0).getTextContent();
                    int minutos = Integer.parseInt(e.getElementsByTagName("minutos").item(0).getTextContent());

                    lista.add(new Pelicula(id, titulo, genero, minutos));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Pelicula p) {
        try {
            File f = new File(fichero);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            NodeList peliculasNode = doc.getElementsByTagName("pelicula");

            for (int i = 0; i < peliculasNode.getLength(); i++) {
                Node node = peliculasNode.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    int idActual = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                    if (idActual == p.getId()) {
                        e.getElementsByTagName("titulo").item(0).setTextContent(p.getTitulo());
                        e.getElementsByTagName("genero").item(0).setTextContent(p.getGenero());
                        e.getElementsByTagName("minutos").item(0).setTextContent(String.valueOf(p.getMinutos()));
                        break;
                    }
                }
            }

            guardarDocumento(doc, f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrar(int id) {
        try {
            File f = new File(fichero);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            NodeList peliculasNode = doc.getElementsByTagName("pelicula");

            for (int i = 0; i < peliculasNode.getLength(); i++) {
                Node node = peliculasNode.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    int idActual = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                    if (idActual == id) {
                        e.getParentNode().removeChild(e);
                        break;
                    }
                }
            }

            guardarDocumento(doc, f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarDocumento(Document doc, File f) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Configuración para XML legible
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc), new StreamResult(f));
    }
}