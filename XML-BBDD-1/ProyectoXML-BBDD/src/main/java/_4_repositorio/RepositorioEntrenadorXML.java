package _4_repositorio;

import _1_vista.Escaner;
import _3_modelo.Entrenador;
import _3_modelo.Pokedex;
import _3_modelo.Pokemon;
import com.mongodb.internal.operation.TransactionOperation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntrenadorXML implements RepositorioEntrenador {
    private final String ficheroEntrada;
    private final File fichero;

    public RepositorioEntrenadorXML(String ficheroEntrada) throws Exception {
        this.ficheroEntrada = ficheroEntrada;
        this.fichero = new File(ficheroEntrada);
        if (!fichero.exists()) {
            crearDocumento();
        }
    }

    /*MÉTODOS REFERENTES AL DOCUMENTO COMO TAL*/
    private void crearDocumento() throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();
            Element raiz = documento.createElement("entrenadores");

            documento.appendChild(raiz);
            guardarDocumento(documento);
        } catch (ParserConfigurationException e) {
            throw new Exception(e);
        }
    }

    private Document cargarDocumento(File fichero) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(fichero);
        } catch (Exception e) {
            throw new Exception("Error cargando documento XML.");
        }
    }

    private void guardarDocumento(Document documento) throws Exception {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(documento), new StreamResult(fichero));
        } catch (TransformerException e) {
            throw new Exception("Error guardando docuemento con transformer XML.");
        }
    }

    /*MÉTODOS HELPERS PARA LETURA O ESCRITURA XML*/
    private String obtenerTextoHijo(Element padre, String nombreHijo) {
        NodeList lista = padre.getElementsByTagName(nombreHijo);
        return lista.getLength() > 0 ? lista.item(0).getTextContent() : "";
    }

    private void cambiarTextoHijo(Element padre, String nombreHijo, String nuevoTexto) {
        NodeList lista = padre.getElementsByTagName(nombreHijo);
        if (lista.getLength() > 0) {
            lista.item(0).setTextContent(nuevoTexto);
        }
    }

    private Pokedex leerPokedexDeElement(Element entrenadorElement) {
        Pokedex pokedex = new Pokedex(151, new ArrayList<Pokemon>());
        NodeList pokemonNodeList = entrenadorElement.getElementsByTagName("pokemons");
        Element pokemonsElement = null;
        NodeList listaDePokemons = null;
        Element elemento = null;
        Node nodo = null;
        int id = -1;
        String nombre = null;
        String especie = null;
        String tipo = null;
        int nivel = -1;

        if (pokemonNodeList.getLength() > 0) {
            pokemonsElement = (Element) pokemonNodeList.item(0);
            listaDePokemons = pokemonsElement.getElementsByTagName("pokemon");
            for (int i = 0; i < listaDePokemons.getLength(); i++) {
                nodo = listaDePokemons.item(i);
                elemento = (Element) nodo;
                id = Integer.parseInt(elemento.getAttribute("id"));
                nombre = obtenerTextoHijo(elemento, "nombre");
                especie = obtenerTextoHijo(elemento, "especie");
                tipo = obtenerTextoHijo(elemento, "tipo");
                nivel = Integer.parseInt(obtenerTextoHijo(elemento, "nivel"));
                pokedex.addPokemon(new Pokemon(id, nombre, especie, tipo, nivel));
            }
        }
        return (pokedex);
    }

    /*MÉTODOS DE LA INTERFÁZ*/
    @Override
    public List<Entrenador> listar() throws Exception {
        List<Entrenador> lista = new ArrayList<Entrenador>();
        File fichero = new File(ficheroEntrada);
        Element elemento = null;
        Node nodo = null;
        Document documento = null;
        NodeList entrenadorNodeList = null;
        Pokedex pokedex = null;
        long id = -1;
        String nombre = null;
        String pueblo = null;
        int edad = -1;
        Entrenador entrenador = null;

        try {
            documento = cargarDocumento(fichero);
            entrenadorNodeList = documento.getElementsByTagName("entrenador");

            for (int i = 0; i < entrenadorNodeList.getLength(); i++) {
                nodo = entrenadorNodeList.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    elemento = (Element) nodo;
                    id = Long.parseLong(elemento.getAttribute("id"));
                    nombre = obtenerTextoHijo(elemento, "nombre");
                    pueblo = obtenerTextoHijo(elemento, "pueblo");
                    edad = Integer.parseInt(obtenerTextoHijo(elemento, "edad"));
                    pokedex = leerPokedexDeElement(elemento);
                    entrenador = new Entrenador(id, nombre, pueblo, edad, pokedex);
                    lista.add(entrenador);
                }
            }
            return (lista);
        } catch (Exception e) {
            throw new Exception("Error listando documento XML.");
        }
    }

    @Override
    public void guardar(Entrenador entrenador) throws Exception {
        Document documento = null;
        Element raiz = null;
        Element entrenadorNode = null;
        Element nombreNode = null;
        Element puebloNode = null;
        Element edadNode = null;
        Element pokemonsNode = null;
        List<Pokemon> listaPokemons = entrenador.getPokedex().getPokemons_obtenidos();
        Pokemon pokemon = null;
        Element pokemonNode = null;
        Element pNombreNode = null;
        Element especieNode = null;
        Element tipoNode = null;
        Element nivelNode = null;

        try {
            documento = cargarDocumento(fichero);
            raiz = documento.getDocumentElement(); // <entrenadores>

            entrenadorNode = documento.createElement("entrenador");
            entrenadorNode.setAttribute("id", String.valueOf(entrenador.getId())); // <entrenador id="1">

            nombreNode = documento.createElement("nombre");
            nombreNode.appendChild(documento.createTextNode(entrenador.getNombre()));
            entrenadorNode.appendChild(nombreNode); // anexa <nombre> en <entrenador>

            puebloNode = documento.createElement("pueblo");
            puebloNode.appendChild(documento.createTextNode(entrenador.getPueblo()));
            entrenadorNode.appendChild(puebloNode); // anexa <pueblo> en <entrenador>

            edadNode = documento.createElement("edad");
            edadNode.appendChild(documento.createTextNode(entrenador.getPueblo()));
            entrenadorNode.appendChild(edadNode); // anexa <edad> en <entrenador>

            pokemonsNode = documento.createElement("pokemons"); // <pokemons>
            for (int i = 0; i < listaPokemons.size(); i++) {
                pokemon = listaPokemons.get(i);

                pokemonNode = documento.createElement("pokemon");
                pokemonNode.setAttribute("id", String.valueOf(pokemon.getId())); // <pokemon id="1">

                pNombreNode = documento.createElement("nombre");
                pNombreNode.appendChild(documento.createTextNode(pokemon.getNombre()));
                pokemonNode.appendChild(pNombreNode); // anexa <nombre> en <pokemon>

                especieNode = documento.createElement("especie");
                especieNode.appendChild(documento.createTextNode(pokemon.getEspecie()));
                pokemonNode.appendChild(especieNode); // anexa <especie> en <pokemon>

                tipoNode = documento.createElement("tipo");
                tipoNode.appendChild(documento.createTextNode(pokemon.getTipo()));
                pokemonNode.appendChild(tipoNode); // anexa <tipo> en <pokemon>

                nivelNode = documento.createElement("nivel");
                nivelNode.appendChild(documento.createTextNode(String.valueOf(pokemon.getNivel())));
                pokemonNode.appendChild(nivelNode); // anexa <nivel> en <pokemon>

                pokemonsNode.appendChild(pokemonNode); // anexa todo el <pokemon> creado en <pokemons>
            }
            entrenadorNode.appendChild(pokemonsNode); // anexa todos los <pokemons> en <entrenador>
            raiz.appendChild(entrenadorNode); // anexamos el <entrenador> a <entrenadores>
            guardarDocumento(documento);
        } catch (Exception e) {
            throw new Exception("Error guardando un entrenador en XML.");
        }
    }

    @Override
    public Entrenador buscarPorId(long id) throws Exception {
        List<Entrenador> lista = listar();
        Entrenador entrenador = null;
        boolean encontrado = false;

        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getId() == id) {
                entrenador = lista.get(i);
                encontrado = true;
            }
        }
        return entrenador;
    }

    @Override
    public Entrenador buscarPorNombre(String nombre) throws Exception {
        List<Entrenador> lista = listar();
        Entrenador entrenador = null;
        boolean encontrado = false;

        for (int i = 0; i < lista.size() && !encontrado; i++) {
            if (lista.get(i).getNombre().equalsIgnoreCase(nombre)) {
                entrenador = lista.get(i);
                encontrado = true;
            }
        }
        return entrenador;
    }

    @Override
    public void actualizar(Entrenador entrenador) throws Exception {

    }

    @Override
    public void borrarPorId(long id) throws Exception {
        boolean borrado = false;
        Document documento = null;
        NodeList listaEntrenadores = null;
        Node nodo = null;
        Element elemento = null;
        long idActual = -1;

        try {
            documento = cargarDocumento(fichero);
            listaEntrenadores = documento.getElementsByTagName("entrenador");
            for (int i = 0; i < listaEntrenadores.getLength() && !borrado; i++) {
                nodo = listaEntrenadores.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    elemento = (Element) nodo;
                    idActual = Long.parseLong(elemento.getAttribute("id"));
                    if (id == idActual) {
                        elemento.getParentNode().removeChild(elemento);
                        borrado = true;
                    }
                }
            }
            if (!borrado) {
                throw new Exception("No se ha encontrado el usuario para borrar.");
            }
            guardarDocumento(documento);
        } catch (Exception e) {
            throw new Exception("Error durante el borrado por ID en XML.");
        }
    }
}
