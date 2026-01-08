package _1_vista;

import _2_controlador.ControladorEntrenador;
import _2_controlador.ControladorEntrenadorConsola;
import _4_repositorio.RepositorioEntrenador;
import _4_repositorio.RepositorioEntrenadorMySQL;
import _4_repositorio.RepositorioEntrenadorJSON;
import _4_repositorio.RepositorioEntrenadorXML;
import _4_repositorio.RepositorioEntrenadorPostgres;
import _4_repositorio.RepositorioEntrenadorMongoDB;
import _5_servicio.ServicioEntrenador;
import _5_servicio.ServicioEntrenadorImpl;

import _6_excepciones.EntidadNoEncontradaException;
import _6_excepciones.EntradaUsuarioNoValidaException;
import _6_excepciones.RepositorioException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

import java.io.IOException;

import java.util.List;
import java.util.Properties;

public class PuntoEntrada {
    private final ControladorEntrenador controlador;

    private DataSource createDataSourceForSQL(boolean postgres) throws IOException {
        String dbUrl;
        String dbUser;
        String dbPass;
        String urlKey = postgres ? "db.pg.url" : "db.url";
        String urlVal = postgres
                ? "jdbc:postgresql://localhost:5433/entrenadores_pokemon"
                : "jdbc:mysql://localhost:3306/entrenadores_pokemon_hikari?useSSL=false&serverTimezone=UTC";
        String userKey = postgres ? "db.pg.user" : "db.user";
        String userVal = postgres ? "postgres" : "jdbcentrenadores";
        String passKey = postgres ? "db.pg.pass" : "db.pass";
        String passVal = postgres ? "postgresmaestro" : "entrenadoresmaestro";

        Properties props = new Properties();
        HikariConfig cfg = new HikariConfig();

        props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        dbUrl = props.getProperty(urlKey, urlVal);
        dbUser = props.getProperty(userKey, userVal);
        dbPass = props.getProperty(passKey, passVal);

        cfg.setJdbcUrl(dbUrl);
        cfg.setUsername(dbUser);
        cfg.setPassword(dbPass);
        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(1);
        cfg.setConnectionTimeout(10_000);

        return new HikariDataSource(cfg);
    }

    private RepositorioEntrenador crearRepositorioDesdeOpcion(int opcionRepo)
            throws RepositorioException, IOException {
        return switch (opcionRepo) {
            case 1 -> new RepositorioEntrenadorXML("datos/entrenadores_pokemon.xml");
            case 2 -> new RepositorioEntrenadorMySQL(createDataSourceForSQL(false));
            case 3 -> new RepositorioEntrenadorJSON(
                    "datos/entrenadores_pokemon.json",
                    "datos/entrenadores_pokemon.json"
            );
            case 4 -> new RepositorioEntrenadorMongoDB();
            case 5 -> new RepositorioEntrenadorPostgres(createDataSourceForSQL(true));
            default -> null;
        };
    }

    public PuntoEntrada() {
        int opcionRepo;
        String ficheroEntrada;
        String ficheroSalida;
        RepositorioEntrenador repo = null;
        ServicioEntrenador servicio;

        Consola.mostrarFraseEndl("Elige el repositorio a utilizar:", Colores.VERDE);
        Consola.mostrarMenu(List.of("Repo XML.", "Repo MySQL.", "Repo JSON.", "Repo MongoDB.", "Repo PostgresSQL"));
        try {
            opcionRepo = Escaner.pedirEntero("Introduce tu opcion: ");
            repo = crearRepositorioDesdeOpcion(opcionRepo);
            if (repo == null) {
                Consola.mostrarFraseEndl("Error, repositorio no valido.", Colores.ROJO);
            }
        } catch (EntradaUsuarioNoValidaException e) {
            Consola.mostrarExcepcion(e);
        } catch (RepositorioException e) {
            Consola.mostrarFraseEndl("Error al crear repositorio.", Colores.ROJO);
        } catch (IOException e) {
            Consola.mostrarFraseEndl("Error al configurar entorno para SQL.", Colores.ROJO);
        }
        if (repo != null) {
            servicio = new ServicioEntrenadorImpl(repo);
            controlador = new ControladorEntrenadorConsola(servicio);
        } else {
            controlador = null;
        }
    }

    private void comunicarConOtroRepositorio() {
        Consola.mostrarFraseEndl("##### COMUNICAR REPOSITORIOS #####", Colores.VERDE);
        Consola.mostrarFraseEndl("Elige el repositorio DESTINO:", Colores.VERDE);
        Consola.mostrarMenu(List.of("Repo XML.", "Repo MySQL.", "Repo JSON.", "Repo MongoDB.", "Repo PostgresSQL"));

        try {
            int opcionDestino = Escaner.pedirEntero("Introduce tu opcion para repositorio destino: ");
            RepositorioEntrenador repoDestino = crearRepositorioDesdeOpcion(opcionDestino);
            if (repoDestino == null) {
                Consola.mostrarFraseEndl("Repositorio destino no válido.", Colores.ROJO);
                return;
            }
            ServicioEntrenador servicioDestino = new ServicioEntrenadorImpl(repoDestino);

            // Toda la lógica del caso de uso se delega al controlador
            controlador.comunicarConOtroRepositorio(servicioDestino);

        } catch (EntradaUsuarioNoValidaException e) {
            Consola.mostrarFraseEndl("Error introduciendo datos: " + e.getMessage(), Colores.ROJO);
        } catch (RepositorioException e) {
            Consola.mostrarFraseEndl("Error al crear repositorio destino.", Colores.ROJO);
        } catch (IOException e) {
            Consola.mostrarFraseEndl("Error al configurar entorno SQL para el repositorio destino.", Colores.ROJO);
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
                Consola.mostrarFraseEndl("OPERACIONES: ");
                Consola.mostrarMenu(List.of("Crear entrenador pokemon.", "Buscar un entrenador pokemon.", "Actualizar datos de entrenador pokemon.", "Borrar entrenador pokemon.", "Comunicar con otro repo.", "Salir del programa."));
                try {
                    opcion_operacion = Escaner.pedirEntero("Introduce tu opcion: ");
                    switch (opcion_operacion) {
                        case 1 -> controlador.crearEntrenador();
                        case 2 -> controlador.buscarEntrenador();
                        case 3 -> controlador.actualizarEntrenador();
                        case 4 -> controlador.borrarEntrenador();
                        case 5 -> comunicarConOtroRepositorio();
                        case 6 -> salir = true;
                    }
                } catch (EntradaUsuarioNoValidaException e) {
                    Consola.mostrarExcepcion(e);
                }
            }
            Consola.mostrarFraseEndl("Saliendo del programa de forma controlada!", Colores.VERDE);
        }
    }
}
