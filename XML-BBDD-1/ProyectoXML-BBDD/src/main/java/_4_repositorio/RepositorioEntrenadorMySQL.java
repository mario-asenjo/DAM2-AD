package _4_repositorio;

import _3_modelo.Entrenador;
import _3_modelo.Pokedex;
import _3_modelo.Pokemon;
import _4_repositorio.conn_utils.MySQLUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntrenadorMySQL implements RepositorioEntrenador {
    private MySQLUtils myUtils;

    public RepositorioEntrenadorMySQL(DataSource dataSource) {
        this.myUtils = new MySQLUtils(dataSource);
    }


    @Override
    public List<Entrenador> listar() throws Exception {
        final String SQL = "SELECT E_ID, E_NOMBRE, E_PUEBLO, E_EDAD, P_ID, P_NOMBRE, P_ESPECIE, P_TIPO, P_NIVEL, P_ID_ENTRENADOR FROM entrenadores_pokemons ORDER BY E_ID, P_ID;";
        return myUtils.ejecutarQuery(SQL, resultSet -> {
            long eId = -1;
            long idEntrenadorActual = -1;
            Pokedex pokedexActual = null;
            Entrenador entrenadorActual = null;
            Pokemon pokemon = null;
            List<Entrenador> retorno = new ArrayList<Entrenador>();

            while (resultSet.next()) {
                eId = resultSet.getLong("E_ID");
                if ( entrenadorActual == null || eId != idEntrenadorActual) {
                    pokedexActual = new Pokedex(151, new ArrayList<Pokemon>());
                    entrenadorActual = new Entrenador(
                            eId,
                            resultSet.getString("E_NOMBRE"),
                            resultSet.getString("E_PUEBLO"),
                            resultSet.getInt("E_EDAD"),
                            pokedexActual
                    );
                    retorno.add(entrenadorActual);
                    idEntrenadorActual = eId;
                }
                pokemon = new Pokemon(
                        resultSet.getInt("P_ID"),
                        resultSet.getString("P_NOMBRE"),
                        resultSet.getString("P_ESPECIE"),
                        resultSet.getString("P_TIPO"),
                        resultSet.getInt("P_NIVEL")
                );
                pokedexActual.addPokemon(pokemon);
            }
            return retorno;
        });
    }

    @Override
    public void guardar(Entrenador entrenador) throws Exception {
        PreparedStatement stmt;
        final String SQLENTRENADOR = "INSERT INTO entrenadores (id, nombre, pueblo, edad) VALUES (?, ?, ?, ?);";
        final String SQLPOKEMON = "INSERT INTO pokemons (id, nombre, especie, tipo, nivel, id_entrenador) VALUES (?, ?, ?, ?, ?, ?);";

        myUtils.ejecutarDML(SQLENTRENADOR,
                entrenador.getId(),
                entrenador.getNombre(),
                entrenador.getPueblo(),
                entrenador.getEdad());
        for (Pokemon pokemon : entrenador.getPokedex().getPokemons_obtenidos()) {
            myUtils.ejecutarDML(SQLPOKEMON,
                    pokemon.getId(),
                    pokemon.getNombre(),
                    pokemon.getEspecie(),
                    pokemon.getTipo(),
                    pokemon.getNivel(),
                    entrenador.getId());
        }
    }

    @Override
    public Entrenador buscarPorId(long id) throws Exception {
        final String SQL = "SELECT E_ID, E_NOMBRE, E_PUEBLO, E_EDAD, P_ID, P_NOMBRE, P_ESPECIE, P_TIPO, P_NIVEL, P_ID_ENTRENADOR FROM entrenadores_pokemons WHERE E_ID = ? ORDER BY P_ID;";

        return myUtils.ejecutarQuery(SQL, resultSet -> {
            Entrenador entrenador = null;
            Pokedex pokedex = null;
            boolean primero = true;

            while (resultSet.next()) {
                if (primero) {
                    pokedex = new Pokedex(151, new ArrayList<Pokemon>());
                    entrenador = new Entrenador(
                            resultSet.getLong("E_ID"),
                            resultSet.getString("E_NOMBRE"),
                            resultSet.getString("E_PUEBLO"),
                            resultSet.getInt("E_EDAD"),
                            pokedex
                    );
                    primero = false;
                }
                pokedex.addPokemon(
                        new Pokemon(
                                resultSet.getInt("P_ID"),
                                resultSet.getString("P_NOMBRE"),
                                resultSet.getString("P_ESPECIE"),
                                resultSet.getString("P_TIPO"),
                                resultSet.getInt("P_NIVEL")
                        )
                );
            }
            return entrenador;
        }, id);
    }

    @Override
    public Entrenador buscarPorNombre(String nombre) throws Exception {
        final String SQL = "SELECT E_ID, E_NOMBRE, E_PUEBLO, E_EDAD, P_ID, P_NOMBRE, P_ESPECIE, P_TIPO, P_NIVEL, P_ID_ENTRENADOR FROM entrenadores_pokemons WHERE E_NOMBRE = ? ORDER BY P_ID;";

        return myUtils.ejecutarQuery(SQL, resultSet -> {
            Entrenador entrenador = null;
            Pokedex pokedex = null;
            boolean primero = true;

            while (resultSet.next()) {
                if (primero) {
                    pokedex = new Pokedex(151, new ArrayList<Pokemon>());
                    entrenador = new Entrenador(
                            resultSet.getLong("E_ID"),
                            resultSet.getString("E_NOMBRE"),
                            resultSet.getString("E_PUEBLO"),
                            resultSet.getInt("E_EDAD"),
                            pokedex
                    );
                    primero = false;
                }
                pokedex.addPokemon(
                        new Pokemon(
                                resultSet.getInt("P_ID"),
                                resultSet.getString("P_NOMBRE"),
                                resultSet.getString("P_ESPECIE"),
                                resultSet.getString("P_TIPO"),
                                resultSet.getInt("P_NIVEL")
                        )
                );
            }
            return entrenador;
        }, nombre);
    }

    @Override
    public void actualizar(Entrenador entrenador) throws Exception {
        final String SQLUPDATEENTRENADOR = "UPDATE entrenadores SET nombre = ?, pueblo = ?, edad = ? WHERE id = ?;";
        final String SQLDELETEPOKEMONS = "DELETE FROM pokemons WHERE id_entrenador = ?;";
        final String SQLINSERTPOKEMONS = "INSERT INTO pokemons (id, nombre, especie, tipo, nivel, id_entrenador) VALUES (?, ?, ?, ?, ?, ?);";

        myUtils.ejecutarDML(SQLUPDATEENTRENADOR,
                entrenador.getNombre(),
                entrenador.getPueblo(),
                entrenador.getEdad(),
                entrenador.getId()
        );
        myUtils.ejecutarDML(SQLDELETEPOKEMONS, entrenador.getId());
        for (Pokemon pokemon : entrenador.getPokedex().getPokemons_obtenidos()) {
            myUtils.ejecutarDML(SQLINSERTPOKEMONS,
                    pokemon.getId(),
                    pokemon.getNombre(),
                    pokemon.getEspecie(),
                    pokemon.getTipo(),
                    pokemon.getNivel(),
                    entrenador.getId()
            );
        }
    }

    @Override
    public void borrarPorId(long id) throws Exception {
        final String SQLDELETEPOKEMONS = "DELETE FROM pokemons WHERE id_entrenador = ?;";
        final String SQLDELETEENTRENADORES = "DELETE FROM entrenadores WHERE id = ?;";

        myUtils.ejecutarDML(SQLDELETEPOKEMONS, id);
        myUtils.ejecutarDML(SQLDELETEENTRENADORES, id);
    }
}
