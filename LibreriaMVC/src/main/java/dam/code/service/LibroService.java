package dam.code.service;

import dam.code.exceptions.LibroException;
import dam.code.model.Libro;
import dam.code.repository.LibroRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibroService {

    private final ObservableList<Libro> libros;
    private final LibroRepository repository;

    public LibroService(LibroRepository repository) {
        this.repository = repository;

        libros = FXCollections.observableArrayList(repository.cargar());
    }

    public ObservableList<Libro> getLibros() {
        return libros;
    }

    public void agregarLibro(Libro libro) throws LibroException {
        validarLibro(libro);
        libros.add(libro);
        guardar();
    }

    public void eliminarLibro(Libro libro) throws LibroException {

        if (libro == null) {
            throw new LibroException("Debe seleccionar un libro");
        }
        libros.remove(libro);
        guardar();
    }

    private void validarLibro(Libro libro) throws LibroException {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new LibroException("El titulo es obligatorio");
        }
        if (libro.getAutor() == null || libro.getAutor().isBlank()) {
            throw new LibroException("El autor es obligatorio");
        }
        if (libro.getPrecio() <= 0) {
            throw new LibroException("El precio debe ser mayor que 0");
        }
        if (libro.getStock() < 0) {
            throw new LibroException("El stock no puede ser negativo");
        }

        boolean existe = libros.stream()
                .anyMatch(l -> l.getTitulo().equalsIgnoreCase(libro.getTitulo()));

        if (existe) {
            throw new LibroException("Ya existe un libro con ese titulo");
        }
    }

    private void guardar() {
        repository.guardar(libros);
    }

    public void actualizarPrecio(Libro libro, double nuevoPrecio) throws LibroException {
        if (nuevoPrecio <= 0) {
            throw new LibroException("El precio debe ser mayor que 0");
        }
        libro.setPrecio(nuevoPrecio);
        guardar();
    }

    public void actualizarStock(Libro libro, int nuevoStock) throws LibroException {
        if (nuevoStock < 0) {
            throw new LibroException("El stock no puede ser negativo");
        }
        libro.setStock(nuevoStock);
        guardar();
    }
}
