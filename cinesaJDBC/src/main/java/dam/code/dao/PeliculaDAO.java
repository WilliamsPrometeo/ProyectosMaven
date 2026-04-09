package dam.code.dao;

import dam.code.models.Pelicula;

import java.util.List;
import java.util.Optional;

public interface PeliculaDAO {
    void save(Pelicula pelicula);
    List<Pelicula> findAll();
    Optional<Pelicula> findById(int id);
    void delete(int id);
}
