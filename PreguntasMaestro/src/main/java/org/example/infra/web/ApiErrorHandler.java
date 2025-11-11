package org.example.infra.web;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.infra.web.dto.ErrorResponseDTO;

import java.sql.SQLException;

public class ApiErrorHandler {
    private ApiErrorHandler() {}

    public static void registrar(Javalin app) {
        app.exception(IllegalArgumentException.class, (ex, ctx) -> responder(ctx, 400, "BadRequest", ex.getMessage()));
        app.exception(SQLException.class, (ex, ctx) -> responder(ctx, 500, "SqlError", ex.getMessage()));
        app.exception(Exception.class, (e, context) -> responder(context, 500, "Internal", "Error inesperado"));
        app.error(404, context -> responder(context, 404, "NotFound", "Ruta no encontrada"));
    }

    private static void responder(Context contexto, int status, String error, String mensaje) {
        contexto.status(status).json(new ErrorResponseDTO(error, mensaje));
    }
}
