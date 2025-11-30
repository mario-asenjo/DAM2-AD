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

public class RepositorioEntrenadorMongoDB implements RepositorioEntrenador {

    private final MongoCollection<Document> collection;

    public RepositorioEntrenadorMongoDB(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public Entrenador buscarPorId(long id) {
        return null;
    }

    @Override
    public void borrarPorId(long id){
        collection.deleteOne(Filters.eq("id", id));
    }
}