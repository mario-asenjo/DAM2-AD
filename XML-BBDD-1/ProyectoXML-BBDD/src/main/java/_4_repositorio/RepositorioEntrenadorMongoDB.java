package _4_repositorio;

import _3_modelo.Pokedex;
import _3_modelo.Pokemon;
import _6_excepciones.EntidadNoEncontradaException;
import _6_excepciones.RepositorioException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import _3_modelo.Entrenador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepositorioEntrenadorMongoDB implements RepositorioEntrenador {

    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    public RepositorioEntrenadorMongoDB() throws RepositorioException {
        MongoClient mongoClient;
        MongoDatabase database;
        String mongoUri;
        String dbName;
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

            mongoUri = props.getProperty("db.mongo.uri", "mongodb://localhost:27017");
            dbName = props.getProperty("db.mongo.name", "entrenadoresPokemon");

            mongoClient = MongoClients.create(mongoUri);
            database = mongoClient.getDatabase(dbName);
        } catch (IOException e) {
            throw new RepositorioException("Error configurando MongoDB.");
        }
        this.mongoClient = mongoClient;
        this.collection = database.getCollection("entrenadores");
    }

    /* ##### HELPERS PRIVADOS ##### */
    private List<Document> pokemonsToDocuments(Pokedex pokedex) {
        List<Document> pokemonsDocs = new ArrayList<Document>();
        Document pDoc = null;

        if (pokedex == null || pokedex.getPokemons_obtenidos() == null) {
            return pokemonsDocs;
        }
        for (Pokemon pokemon : pokedex.getPokemons_obtenidos()) {
            pDoc = new Document()
                    .append("id", pokemon.getId())
                    .append("nombre", pokemon.getNombre())
                    .append("especie", pokemon.getEspecie())
                    .append("tipo", pokemon.getTipo())
                    .append("nivel", pokemon.getNivel());
            pokemonsDocs.add(pDoc);
        }
        return pokemonsDocs;
    }

    private Pokedex pokedexFromDocument(Document pokedexDocument) {
        List<Pokemon> lista = new ArrayList<Pokemon>();
        Object pokemonRaw = pokedexDocument.get("pokemons");
        int id = -1;
        String nombre = null;
        String especie = null;
        String tipo = null;
        int nivel = -1;

        if (pokemonRaw instanceof List<?> rawList) {
            for (Object objectPDoc : rawList) {
                if (objectPDoc instanceof Document pDoc) {
                    id = pDoc.getInteger("id");
                    nombre = pDoc.getString("nombre");
                    especie = pDoc.getString("especie");
                    tipo = pDoc.getString("tipo");
                    nivel = pDoc.getInteger("nivel");
                    lista.add(new Pokemon(id, nombre, especie, tipo, nivel));
                }
            }
        }
        return new Pokedex(151, lista);
    }

    private Entrenador entrenadorFromDocument(Document entrenadorDocument) {
        long id = entrenadorDocument.getLong("id");
        String nombre = entrenadorDocument.getString("nombre");
        String pueblo = entrenadorDocument.getString("pueblo");
        int edad = entrenadorDocument.getInteger("edad");
        Pokedex pokedex = pokedexFromDocument(entrenadorDocument);

        return new Entrenador(id, nombre, pueblo, edad, pokedex);
    }

    /* ##### IMPLEMENTACIÓN DE LA INTERFAZ ##### */
    @Override
    public void guardar(Entrenador entrenador) {
        Document docEntrenador = null;
        List<Document> docsPokemons = pokemonsToDocuments(entrenador.getPokedex());

        docEntrenador = new Document()
                .append("id", entrenador.getId())
                .append("nombre", entrenador.getNombre())
                .append("pueblo", entrenador.getPueblo())
                .append("edad", entrenador.getEdad())
                .append("pokemons", docsPokemons);
        collection.insertOne(docEntrenador);
    }

    @Override
    public List<Entrenador> listar() {
        List<Entrenador> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            lista.add(entrenadorFromDocument(doc));
        }
        return lista;
    }

    @Override
    public void actualizar(Entrenador entrenador) {
        List<Document> pokemonsDocs = pokemonsToDocuments(entrenador.getPokedex());

        collection.updateOne(
                Filters.eq("id", entrenador.getId()),
                Updates.combine(
                        Updates.set("nombre", entrenador.getNombre()),
                        Updates.set("pueblo", entrenador.getPueblo()),
                        Updates.set("edad", entrenador.getEdad()),
                        Updates.set("pokemons", pokemonsDocs)
                )
        );
    }

    @Override
    public Entrenador buscarPorId(long id) throws EntidadNoEncontradaException {
        Document documentoEntrenador = collection.find(Filters.eq("id", id)).first();

        if (documentoEntrenador == null) {
            throw new EntidadNoEncontradaException("No se ha encontrado entrenador con este ID.");
        }
        return entrenadorFromDocument(documentoEntrenador);
    }

    @Override
    public Entrenador buscarPorNombre(String nombre) throws EntidadNoEncontradaException {
        FindIterable<Document> documentos = collection.find();
        String nombreDocumento = null;

        // Hacemos filtro en java para mantener equalsIgnoreCase.
        for (Document documento : documentos) {
            nombreDocumento = documento.getString("nombre");
            if (nombreDocumento != null && nombreDocumento.equalsIgnoreCase(nombre)) {
                return entrenadorFromDocument(documento);
            }
        }
         throw new EntidadNoEncontradaException("No se ha encontrado entrenador con este nombre.");
    }

    @Override
    public void borrarPorId(long id){
        collection.deleteOne(Filters.eq("id", id));
    }


}