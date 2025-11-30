package _4_repositorio.conn_utils;

import javax.sql.DataSource;
import java.sql.*;

public class MySQLUtils {
    private final DataSource dataSource;

    public MySQLUtils(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /*---------------- API PUBLICA (abren y cierran conexión) ----------------*/

    public int ejecutarDML(String sql, Object... parametros) throws SQLException {
        Connection conexion = dataSource.getConnection();
        PreparedStatement stmt = conexion.prepareStatement(sql);
        int filasAfectadas;

        bindParams(stmt, parametros);
        filasAfectadas = stmt.executeUpdate();
        stmt.close();
        conexion.close();
        return (filasAfectadas);
    }

    public <T> T ejecutarQuery(String sql, Mapper<T> mapper, Object... parametros) throws SQLException {
        Connection conexion = dataSource.getConnection();
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet res;
        T retorno;

        bindParams(stmt, parametros);
        res = stmt.executeQuery();
        retorno = mapper.miMetodoMapper(res);
        res.close();
        stmt.close();
        conexion.close();
        return retorno;
    }

    /*---------------- Transacción / bloque con la misma conexión ----------------*/

    public <T> T inTransaction(SQLFunction<Connection, T> work) throws SQLException {
        Connection conexion = dataSource.getConnection();
        boolean prev = conexion.getAutoCommit();
        T result;
        try {
            conexion.setAutoCommit(false);
            result = work.apply(conexion);
            conexion.commit();
            return result;
        } catch (Exception e) {
            conexion.rollback();
            if (e instanceof SQLException se) throw se;
            throw new SQLException("Error en transacción", e);
        } finally {
            conexion.setAutoCommit(prev);
        }
    }

    /*---------------- Metodos sobrecargados que aceptan Connection ----------------*/

    public int ejecutarDML(Connection conexion, String sql, Object... parametros) throws SQLException {
        int filasAfectadas;
        PreparedStatement stmt = conexion.prepareStatement(sql);

        bindParams(stmt, parametros);
        filasAfectadas = stmt.executeUpdate();
        stmt.close();
        return (filasAfectadas);
    }

    public <T> T ejecutarQuery(Connection conexion, String sql, Mapper<T> mapper, Object... parametros) throws SQLException {
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet res;
        T retorno;

        bindParams(stmt, parametros);
        res = stmt.executeQuery();
        retorno = mapper.miMetodoMapper(res);
        res.close();
        stmt.close();
        return retorno;
    }

    /*---------------- Utilidades ----------------*/

    private void bindParams(PreparedStatement stmt, Object... parametros) throws SQLException {
        Object p;
        int iParams;

        for (int i = 0; i < parametros.length; i++) {
            p = parametros[i];
            iParams = i + 1;
            if (p == null) {
                stmt.setNull(iParams, Types.VARCHAR);
            } else if (p instanceof String s) {
                stmt.setString(iParams, s);
            } else if (p instanceof Character c) {
                stmt.setString(iParams, String.valueOf(c));
            } else if (p instanceof Integer n) {
                stmt.setInt(iParams, n);
            } else if (p instanceof Long n) {
                stmt.setLong(iParams, n);
            } else if (p instanceof Boolean b) {
                stmt.setBoolean(iParams, b);
            } else if (p instanceof Date d) {
                stmt.setDate(iParams, d);
            } else if (p instanceof Timestamp ts) {
                stmt.setTimestamp(iParams, ts);
            } else {
                stmt.setObject(iParams, p);
            }
        }
    }

    @FunctionalInterface
    public interface Mapper<T> {
        T miMetodoMapper(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLFunction<Con, Res> {
        Res apply(Con connection) throws Exception;
    }


}
