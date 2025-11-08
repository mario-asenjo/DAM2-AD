package repositorio;

import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BBDD {
    Connection conexion;

    public BBDD(Connection conexion) throws SQLException {
        this.conexion = conexion;
    }

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
            } else if (p instanceof java.sql.Date d) {
                stmt.setDate(iParams, d);
            } else if (p instanceof java.sql.Timestamp ts) {
                stmt.setTimestamp(iParams, ts);
            } else {
                stmt.setObject(iParams, p);
            }
        }
    }

    public int ejecutarDML(String sql, Object... parametros) throws SQLException {
        PreparedStatement stmt = conexion.prepareStatement(sql);
        int filasAfectadas;

        bindParams(stmt, parametros);
        filasAfectadas = stmt.executeUpdate();
        stmt.close();
        return (filasAfectadas);
    }

    public ResultSet ejecutarQuery(String sql, Object... parametros) throws SQLException {
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet res;

        bindParams(stmt, parametros);
        res = stmt.executeQuery();
        stmt.close();
        return res;
    }

    public <T> T ejecutarQuery(String sql, Mapper<T> mapper, Object... parametros) throws SQLException {
        PreparedStatement stmt;
        ResultSet res;
        T retorno;

        stmt = conexion.prepareStatement(sql);
        bindParams(stmt, parametros);
        res = stmt.executeQuery();
        retorno = mapper.miMetodoMapper(res);
        res.close();
        stmt.close();
        return retorno;
    }

    public boolean isConnected() throws SQLException {
        return !this.conexion.isClosed();
    }

    public void closeConnection() throws SQLException {
        this.conexion.close();
    }

    @FunctionalInterface
    public interface Mapper<T> {
        T miMetodoMapper(ResultSet resultSet) throws SQLException;
    }
}
