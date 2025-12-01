package _4_repositorio;

import javax.sql.DataSource;

public class RepositorioEntrenadorPostgres extends RepositorioEntrenadorSQL {
    public RepositorioEntrenadorPostgres(DataSource dataSource) {
        super(dataSource);
    }
}
