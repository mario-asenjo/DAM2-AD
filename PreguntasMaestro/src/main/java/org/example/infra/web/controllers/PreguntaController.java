package org.example.infra.web.controllers;

import io.javalin.http.Context;
import org.example.config.AppConfig;
import org.example.domain.model.Opcion;
import org.example.domain.model.Pregunta;
import org.example.infra.web.dto.PreguntaDTOs.PreguntaDTO;
import org.example.infra.web.dto.PreguntaDTOs.OpcionDTO;
import org.example.infra.web.validation.Validators;

import java.util.ArrayList;
import java.util.List;

public class PreguntaController {
    private final AppConfig app;
    public PreguntaController(AppConfig app) {
        this.app = app;
    }

    /** GET /api/v1/pregunta?count=N */
    public void obtenerAleatorias(Context contexto) throws Exception {
        int count = Validators.mustBeIntInRange(contexto.queryParam("count"), 1, 1000, "count");
        List<Pregunta> preguntas = app.randomSvc().obtener(count);

        List<PreguntaDTO> retorno = new ArrayList<>(preguntas.size());
        for (Pregunta pregunta : preguntas) {
            List<OpcionDTO> opciones = new ArrayList<>(pregunta.getOpciones().size());
            for (Opcion opcion : pregunta.getOpciones()) {
                opciones.add(new OpcionDTO(String.valueOf(opcion.getOpcion()), opcion.getTexto()));
            }
            retorno.add(new PreguntaDTO(pregunta.getId(), pregunta.getEnunciado(), opciones));
        }
        contexto.json(retorno);
    }
}
