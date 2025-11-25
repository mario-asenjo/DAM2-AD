package _4_repositorio;

import _3_modelo.Pokemon;
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

public class RepositorioEntrenadorMongoDB {

    private MongoCollection<Document> collection = null;

    public RepositorioEntrenadorMongoDB() {
        MongoClient mongoClient;
        MongoDatabase database;

        try {
            // Cargamos propiedades desde application.properties
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

            String mongoUri = props.getProperty("db.mongo.uri", "mongodb://localhost:27017");
            String dbName = props.getProperty("db.mongo.name", "cine");

            // Creamos la conexión con MongoDB
            mongoClient = MongoClients.create(mongoUri);
            database = mongoClient.getDatabase(dbName);

            // Colección donde almacenaremos películas
            collection = database.getCollection("peliculas");

            // Cerramos la conexión con MongoDB
            mongoClient.close();
        } catch (NullPointerException | SecurityException | IOException e) {
            System.out.println("Error configurando MongoDB: " + e.getMessage());
        }
    }

    // Inserta una película
    public void guardar(Entrenador entrenador) {
        Document docEntrenador = new Document()
                .append("id", entrenador.getId())
                .append("nombre", entrenador.getNombre())
                .append("pueblo", entrenador.getPueblo())
                .append("edad", entrenador.getEdad());
        Document docPokemons = new Document();
        for (Pokemon pokemon : entrenador.getPokedex().getPokemons_obtenidos()) {
            docPokemons.append("id", pokemon.getId());
            docPokemons.append("nombre", pokemon.getNombre());
            docPokemons.append("tipo", pokemon.getTipo());
            docPokemons.append("nivel", pokemon.getNivel());
        }
        collection.insertOne(docEntrenador);
    }

    // Devuelve todas las películas
    public List<Entrenador> listar() {
        List<Entrenador> lista = new ArrayList<>();
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            lista.add(new Entrenador(
                    doc.getInteger("id"),
                    doc.getString("nombre"),
                    doc.getString("pueblo"),
                    doc.getInteger("edad")
            ));
        }
        return lista;
    }

    // Actualiza una película por id
    public void actualizar(Entrenador p) {
        collection.updateOne(
                Filters.eq("id", p.getId()),
                Updates.combine(
                        Updates.set("nombre", p.getNombre()),
                        Updates.set("pueblo", p.getPueblo()),
                        Updates.set("edad", p.getEdad())
                )
        );
    }

    // Borra una película por id
    public void borrar(int id) {
        collection.deleteOne(Filters.eq("id", id));
    }
}