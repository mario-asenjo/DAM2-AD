package org.example.infra.web.controllers;

import org.example.config.AppConfig;
import io.javalin.http.Context;
import org.example.domain.ports.ExamCatalog.ExamenMeta;
import org.example.infra.web.dto.ExamDTO;

import java.util.ArrayList;
import java.util.List;

public class ExamController {
    private final AppConfig app;

    public ExamController(AppConfig app) {
        this.app = app;
    }

    /** GET /api/v1/examenes/ */
    public void listarExamenes(Context contexto) {
        List<ExamenMeta> metas = app.listarExamenes();
        List<ExamDTO> retorno = new ArrayList<>(metas.size());

        for (var meta : metas) {
            retorno.add(new ExamDTO(meta.nombre(), meta.bytes(), meta.lastModified()));
        }
        contexto.json(retorno);
    }
}
