package dam.code.dao.impl;

import dam.code.dao.PeliculaDAO;
import dam.code.exceptions.PeliculaException;
import dam.code.models.Pelicula;

import java.util.List;
import java.util.Optional;

public class PeliculaDAOImpl implements PeliculaDAO {

    @Override
    public void registrar(Pelicula pelicula) throws PeliculaException {

    }

    @Override
    public List<Pelicula> listar() throws PeliculaException {
        return List.of();
    }

    @Override
    public List<Pelicula> obtenerPeliculasPorUsuario(int idUsuario) throws PeliculaException {
        return List.of();
    }

    @Override
    public void visualizar(int idUsuario, int idPelicula) throws PeliculaException {

    }
}
