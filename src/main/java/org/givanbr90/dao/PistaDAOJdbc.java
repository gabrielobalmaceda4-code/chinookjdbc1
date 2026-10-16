package org.givanbr90.dao;

import org.givanbr90.conexion.ConexionBD;
import org.givanbr90.modelo.Genero;
import org.givanbr90.modelo.Pista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PistaDAOJdbc implements PistaDAO {

    private static final String SELECT_PISTA =
            "SELECT t.TrackId, t.Name, t.Composer, t.Milliseconds, t.UnitPrice, "
                    + "g.GenreId, g.Name AS GenreName "
                    + "FROM Track t JOIN Genre g ON g.GenreId = t.GenreId ";

    @Override
    public List<Pista> findByGenero(int generoId) throws SQLException {
        String sql = SELECT_PISTA + "WHERE t.GenreId = ? ORDER BY t.Name";
        return consultar(sql, generoId);
    }

    @Override
    public List<Pista> findByAlbum(int albumId) throws SQLException {
        // TODO 1: completa el SQL: filtra por t.AlbumId y ordena por t.TrackId
        String sql = SELECT_PISTA + "";
        // TODO 2: devuelve el resultado de consultar(...), igual que en findByGenero
        return List.of();
    }

    private List<Pista> consultar(String sql, int parametro) throws SQLException {
        List<Pista> pistas = new ArrayList<>();

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, parametro);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    pistas.add(mapearPista(resultado));
                }
            }
        }
        return pistas;
    }

    private Pista mapearPista(ResultSet resultado) throws SQLException {
        Pista pista = new Pista();
        pista.setId(resultado.getInt("TrackId"));
        pista.setNombre(resultado.getString("Name"));
        pista.setCompositor(resultado.getString("Composer"));
        // TODO 3: guarda los milisegundos (columna Milliseconds)
        // TODO 4: guarda el precio (columna UnitPrice) con getBigDecimal
        // TODO 5: crea un Genero con las columnas GenreId y GenreName y guárdalo en la pista
        return pista;
    }
}