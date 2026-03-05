package dam.code.repository;

import dam.code.model.Libro;

import java.util.List;

public interface LibroRepository {

    List<Libro> cargar();

    void guardar(List<Libro> libros);
}
