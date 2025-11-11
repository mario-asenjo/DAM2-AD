package org.example.infra.web;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import org.example.config.AppConfig;
import org.example.infra.web.controllers.ExamController;
import org.example.infra.web.controllers.ImportController;
import org.example.infra.web.controllers.PreguntaController;
import org.example.infra.web.controllers.RespuestaController;

public class ServerBootStrap {
    private Javalin app;

    public void start(AppConfig appCfg, int port) {
        ExamController examen = new ExamController(appCfg);
        ImportController importer = new ImportController(appCfg);
        PreguntaController pregunta = new PreguntaController(appCfg);
        RespuestaController respuesta = new RespuestaController(appCfg);

        app = Javalin.create(
                javalinCfg -> {
                    javalinCfg.bundledPlugins.enableCors(
                            cors -> cors.addRule(
                                    r -> r.anyHost()
                            )
                    );
                    javalinCfg.jsonMapper(new JavalinJackson());

                }
        );

        ApiErrorHandler.registrar(app);

        app.get("/health", ctx -> ctx.result("STATUS -> OK"));

        app.get("/api/v1/examenes", examen::listarExamenes);
        app.get("/api/v1/preguntas", pregunta::obtenerAleatorias);
        app.post("/api/v1/importar", importer::importFromFile);
        app.post("/api/v1/respuestas/comprobar", respuesta::comprobar);

        app.start(port);
        System.out.println("HTTP Server escuchando en http://localhost:"+port);
    }

    public void stop() {
        if (app != null) {
            app.stop();
        }
    }
}
