package dam.code.dao.impl;

import dam.code.dao.PeliculaDAO;
import dam.code.models.Pelicula;

import java.util.List;
import java.util.Optional;

public class PeliculaDAOImpl implements PeliculaDAO {
    @Override
    public void save(Pelicula pelicula) {

    }

    @Override
    public List<Pelicula> findAll() {
        return List.of();
    }

    @Override
    public Optional<Pelicula> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void delete(int id) {

    }
}
