package org.example.infra.web.controllers;

import io.javalin.http.Context;
import org.example.config.AppConfig;
import org.example.infra.web.dto.ImportDtos.ImportRequest;
import org.example.infra.web.dto.ImportDtos.ImportResponse;
import org.example.infra.web.validation.Validators;

public class ImportController {
    private final AppConfig app;

    public ImportController(AppConfig app) {
        this.app = app;
    }

    /** POST /api/v1/import body: {"filename": "examen_x.txt"} */
    public void importFromFile(Context contexto) throws Exception {
        ImportRequest req = contexto.bodyAsClass(ImportRequest.class);
        String filename = Validators.mustBeTxtFile(req.filename);
        var res = app.importarDesde(filename);
        contexto.json(new ImportResponse(res.preguntasInsertadas(), res.opcionesInsertadas()));
    }
}
