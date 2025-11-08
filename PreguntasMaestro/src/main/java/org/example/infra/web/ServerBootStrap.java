package org.example.infra.web;

import io.javalin.Javalin;

public class ServerBootStrap {
    public void start(int port) {
        Javalin app = Javalin.create(
                cfg -> {
                    cfg.bundledPlugins.enableCors(
                            cors -> cors.addRule(
                                    r -> r.anyHost()
                            )
                    );
                }
        );

        app.get("/health", ctx -> ctx.result("OK"));

        app.start(port);
        System.out.println("HTTP Server on http://localhost:"+port);
    }
}
