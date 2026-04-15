package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.PeliculaDAO;
import dam.code.exceptions.PeliculaException;
import dam.code.models.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAOImpl implements PeliculaDAO {

    @Override
    public void registrar(Pelicula pelicula) throws PeliculaException {
        String sql = "INSERT INTO peliculas (titulo, director, duracion, fecha_publicacion) VALUES (?, ?, ?, ?)";

        try (Connection conexion = DatabaseConfig.getConnection();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setInt(3, pelicula.getDuracion());
            ps.setDate(4, Date.valueOf(pelicula.getFecha_publicacion()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PeliculaException(e.getMessage());
        }
    }

    @Override
    public List<Pelicula> listar() throws PeliculaException {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM peliculas";

        try (Connection con = DatabaseConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                peliculas.add(new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getInt("duracion"),
                        rs.getDate("fecha_publicacion").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            throw new PeliculaException(e.getMessage());
        }

        return peliculas;
    }

    @Override
    public List<Pelicula> obtenerPeliculasPorUsuario(int idUsuario) throws PeliculaException {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = """
                SELECT p.id, p.titulo, p.director, p.duracion, p.fecha_publicacion,
                    COUNT(v.id_pelicula) AS visualizaciones
                FROM peliculas p
                INNER JOIN visualizaciones v ON p.id = v.id_pelicula
                WHERE v.id_usuario = ?
                GROUP BY p.id, p.titulo, p.director, p.duracion, p.fecha_publicacion
                """;

        try (Connection conexion = DatabaseConfig.getConnection();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pelicula pelicula = new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getInt("duracion"),
                        rs.getDate("fecha_publicacion").toLocalDate()
                );
                pelicula.setVisualizaciones(rs.getInt("visualizaciones"));
                peliculas.add(pelicula);
            }

        } catch (SQLException e) {
            throw new PeliculaException(e.getMessage());
        }

        return peliculas;
    }

    @Override
    public void visualizar(int idUsuario, int idPelicula) throws PeliculaException {
        String sql = "INSERT INTO visualizaciones (id_usuario, id_pelicula) VALUES (?, ?)";

        try (Connection con = DatabaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPelicula);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PeliculaException(e.getMessage());
        }
    }
}
