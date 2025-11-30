package _1_vista;

import _2_controlador.*;
import _4_repositorio.*;
import _5_servicio.ServicioEntrenadorImpl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bson.Document;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class PuntoEntrada {
    private final ControladorEntrenadorConsola controlador;

    private DataSource createDataSourceForMySQL() throws IOException {
        String dbUrl;
        String dbName;
        String dbPass;
        Properties props = new Properties();
        HikariConfig cfg = new HikariConfig();

        props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        dbUrl = props.getProperty("db.url", "jdbc:mysql://localhost:3306/entrenadores_pokemon_hikari?useSSL=false&serverTimezone=UTC");
        dbName = props.getProperty("db.user", "jdbcuser");
        dbPass = props.getProperty("db.pass", "12345");

        cfg.setJdbcUrl(dbUrl);
        cfg.setUsername(dbName);
        cfg.setPassword(dbPass);
        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(1);
        cfg.setConnectionTimeout(10_000);

        return new HikariDataSource(cfg);
    }

    private MongoCollection<Document> createDataSourceForMongo() throws IOException {
        MongoClient mongoClient;
        MongoDatabase database;
        MongoCollection<Document> collection;
        String mongoUri;
        String dbName;
        Properties props = new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

        mongoUri = props.getProperty("db.mongo.uri", "mongodb://localhost:27017");
        dbName = props.getProperty("db.mongo.name", "cine");

        mongoClient = MongoClients.create(mongoUri);
        database = mongoClient.getDatabase(dbName);

        collection = database.getCollection("peliculas");

        mongoClient.close();

        return collection;
    }

    public PuntoEntrada() {
        int opcionRepo;
        String ficheroEntrada;
        String ficheroSalida;
        RepositorioEntrenador repo = null;
        ServicioEntrenadorImpl servicio;

        Consola.mostrarFraseEndl("Elige el repositorio a utilizar:", Colores.VERDE);
        Consola.mostrarMenu(List.of("Repo XML.", "Repo MySQL.", "Repo JSON.", "Repo MongoDB."));
        opcionRepo = Escaner.pedirEntero("Introduce tu opcion: ");
        ficheroEntrada = Escaner.pedirString("Introduce el nombre del fichero de entrada: ");
        ficheroSalida = Escaner.pedirString("Introduce el nombre del fichero de salida: ");
        try {
            switch (opcionRepo) {
                case 1 -> repo = new RepositorioEntrenadorXML(ficheroEntrada, ficheroSalida);
                case 2 -> repo = new RepositorioEntrenadorMySQL(createDataSourceForMySQL());
                case 3 -> repo = new RepositorioEntrenadorJSON(ficheroEntrada, ficheroSalida);
                case 4 -> repo = new RepositorioEntrenadorMongoDB(createDataSourceForMongo());
                default -> Consola.mostrarFraseEndl("Error, repositorio no valido.", Colores.ROJO);
            }
        } catch (NullPointerException | SecurityException | IOException e) {
            Consola.mostrarExcepcion(e);
        }
        if (repo != null) {
            servicio = new ServicioEntrenadorImpl(repo);
            controlador = new ControladorEntrenadorConsola(servicio);
        } else {
            controlador = null;
        }
    }

    public void iniciar() {
        boolean salir;
        int opcion_operacion;

        if (controlador == null) {
            Consola.mostrarFraseEndl("No has elegido un repositorio correcto. Saliendo del programa.", Colores.ROJO);
        } else {
            salir = false;

            Consola.mostrarFraseEndl("##### PROGRAMA DE ENTRENADORES POKEMON #####");
            while (!salir) {
                try {
                    Consola.mostrarFraseEndl("OPERACIONES: ");
                    Consola.mostrarMenu(List.of("Crear entrenador pokemon.", "Buscar un entrenador pokemon.", "Actualizar datos de entrenador pokemon.", "Borrar entrenador pokemon.", "Salir del programa."));
                    opcion_operacion = Escaner.pedirEntero("Introduce tu opcion: ");
                    switch (opcion_operacion) {
                        case 1 -> controlador.crearEntrenador();
                        case 2 -> controlador.buscarEntrenador();
                        case 3 -> controlador.actualizarEntrenador();
                        case 4 -> controlador.borrarEntrenador();
                        case 5 -> salir = true;
                    }
                } catch (Exception e) {
                    Consola.mostrarExcepcion(e);
                }
            }
            Consola.mostrarFraseEndl("Saliendo del programa de forma controlada!", Colores.VERDE);
        }
    }
}
