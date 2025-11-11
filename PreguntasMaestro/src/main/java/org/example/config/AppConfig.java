package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.app.ComprobarRespuestaService;
import org.example.app.ImportarPreguntasService;
import org.example.app.ObtenerPreguntasAleatoriasService;
import org.example.domain.ports.ExamCatalog;
import org.example.domain.ports.ImporterFactory;
import org.example.domain.ports.PreguntaImporter;
import org.example.domain.ports.PreguntaRepository;
import org.example.infra.db.BBDD;
import org.example.infra.db.BBDDPreguntasRepository;
import org.example.infra.file.FsExamCatalog;
import org.example.infra.importer.FicheroImporterFactory;
import org.example.infra.web.ServerBootStrap;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

public class AppConfig {
    // Casos de uso
    private ImportarPreguntasService importarPreguntasService;
    private ObtenerPreguntasAleatoriasService obtenerPreguntasAleatoriasService;
    private ComprobarRespuestaService comprobarRespuestaService;

    // Puertos auxiliares
    private ExamCatalog catalog;
    private ImporterFactory importerFactory;

    // Infra
    private DataSource dataSource;
    private BBDD bd;

    // HTTP Server
    private ServerBootStrap httpServer;

    public void start(boolean withHttp){
        String baseDir;
        dataSource = buildDataSourceFromEnv();
        bd = new BBDD(dataSource);
        PreguntaRepository repo = new BBDDPreguntasRepository(bd);

        this.importarPreguntasService =  new ImportarPreguntasService(repo);
        this.obtenerPreguntasAleatoriasService = new ObtenerPreguntasAleatoriasService(repo);
        this.comprobarRespuestaService = new ComprobarRespuestaService(repo);
        baseDir = getenv("EXAMS_DIR", "Data");
        this.catalog = new FsExamCatalog(Path.of(baseDir), "txt");
        this.importerFactory = new FicheroImporterFactory(baseDir);

        if (withHttp) {
            httpServer = new ServerBootStrap();
            httpServer.start(this,8080);
        }
        System.out.println("AppConfig listo. withHttp -> " + withHttp);
    }

    public void stop() {
        if (httpServer != null) {
            httpServer.stop();
        }
        if (dataSource instanceof HikariDataSource ds) {
            ds.close();
        }
    }

    // ==== Helpers para adaptadores de entrada (CLI/GUI/REST) ====

    /** Lista los exámenes disponibles (nombre, tamaño, fecha). */
    public List<ExamCatalog.ExamenMeta> listarExamenes() {
        return catalog.listar();
    }

    /** Importa preguntas desde un nombre de fichero relativo al directorio base. */
    public ImportarPreguntasService.Resultado importarDesde(String nombreArchivo) throws Exception {
        PreguntaImporter importer = importerFactory.fromPath(nombreArchivo);
        return importarPreguntasService.importar(importer);
    }

    // ==== Getters para casos de uso que usará CLI/REST/GUI ====

    public ImportarPreguntasService importarSvc() { return importarPreguntasService; }
    public ObtenerPreguntasAleatoriasService randomSvc() { return obtenerPreguntasAleatoriasService; }
    public ComprobarRespuestaService checkSvc() { return comprobarRespuestaService; }

    // ==== Internos ====

    private DataSource buildDataSourceFromEnv() {
        String url  = getenv("DB_URL",  "jdbc:mysql://localhost:3306/jdbc_preguntas_buena?useSSL=false&serverTimezone=UTC");
        String user = getenv("DB_USER", "jdbcuser");
        String pass = getenv("DB_PASS", "12345");

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(url);
        cfg.setUsername(user);
        cfg.setPassword(pass);
        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(1);
        cfg.setConnectionTimeout(10_000);

        return new HikariDataSource(cfg);
    }

    private static String getenv(String k, String def) {
        String v = System.getenv(k);
        return (v == null || v.isBlank()) ? def : v;
    }
}

