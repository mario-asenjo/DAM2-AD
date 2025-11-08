package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    public static DataSource createFromEnv() {
        String url = getenv("DB_URL", "jdbc:mysql://localhost:3306/jdbc_preguntas_buena?useSSL=false&serverTimezone=UTC");
        String user = getenv("DB_USER", "jdbcuser");
        String pass = getenv("DB_PASS", "12345");

        return new HikariDataSource(
                initConfig(url, user, pass)
        );
    }


    private static HikariConfig initConfig(String url, String user, String pass) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(10000);
        return config;
    }

    private static String getenv(String env, String hard) {
        return (System.getenv(env) == null || System.getenv(env).isBlank()) ? hard : env;
    }
}
