package org.example.infra.web.controllers;

import io.javalin.http.Context;
import org.example.config.AppConfig;
import org.example.infra.web.dto.ComprobarOpcionDTOs.ComprobarOpcionResponse;
import org.example.infra.web.dto.ComprobarOpcionDTOs.ComprobarOpcionRequest;
import org.example.infra.web.validation.Validators;

public class RespuestaController {
    private final AppConfig app;

    public RespuestaController(AppConfig app) {
        this.app = app;
    }

    /** POST /api/v1/respuestas/comprobar body: { "preguntaId": 12, "opcion": "B" } */
    public void comprobar(Context contexto) throws Exception {
        ComprobarOpcionRequest req = contexto.bodyAsClass(ComprobarOpcionRequest.class);
        if (req == null) {
            throw new IllegalArgumentException("Body requerido");
        }
        if (req.preguntaId <= 0) {
            throw new IllegalArgumentException("preguntaId debe ser > 0");
        }
        char opcion = Validators.mustBeOptionABCD(req.opcion,"opcion");
        boolean ok = app.checkSvc().esCorrecta(req.preguntaId, opcion);
        contexto.json(new ComprobarOpcionResponse(ok));
    }
}
