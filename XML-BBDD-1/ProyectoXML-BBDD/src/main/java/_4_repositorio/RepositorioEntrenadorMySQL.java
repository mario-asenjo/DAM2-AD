package _4_repositorio;

import javax.sql.DataSource;

public class RepositorioEntrenadorMySQL extends RepositorioEntrenadorSQL {
    public RepositorioEntrenadorMySQL(DataSource dataSource) {
        super(dataSource);
    }
}
