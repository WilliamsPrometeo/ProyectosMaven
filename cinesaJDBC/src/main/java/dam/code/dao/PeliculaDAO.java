package dam.code.dao;

import dam.code.exceptions.PeliculaException;
import dam.code.exceptions.UsuarioException;
import dam.code.models.Pelicula;

import java.util.List;
import java.util.Optional;

public interface PeliculaDAO {
    void registrar(Pelicula pelicula) throws PeliculaException;
    List<Pelicula> listar() throws PeliculaException;
    List<Pelicula> obtenerPeliculasPorUsuario(int idUsuario) throws PeliculaException;
    void visualizar(int idUsuario, int idPelicula) throws PeliculaException;
}
