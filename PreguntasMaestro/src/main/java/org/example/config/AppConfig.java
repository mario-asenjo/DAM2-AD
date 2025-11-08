package org.example.config;

import org.example.infra.web.ServerBootStrap;

import javax.sql.DataSource;

public class AppConfig {
    public void start() {
        DataSource dataSource = DataSourceFactory.createFromEnv();
        ServerBootStrap http = new ServerBootStrap();
        http.start(8080);
    }
}
