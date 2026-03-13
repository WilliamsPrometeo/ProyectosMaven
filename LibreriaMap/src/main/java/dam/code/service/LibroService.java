package dam.code.service;

import dam.code.dto.LibroDTO;
import dam.code.exceptions.LibroException;
import dam.code.model.Libro;
import dam.code.repository.LibroRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibroService {

    private final ObservableList<Libro> libros;
    private final LibroRepository repository;
    private final Map<LibroDTO, String> editoriales;

    public LibroService(LibroRepository repository) {
        this.repository = repository;
        editoriales = repository.cargar();
        libros = FXCollections.observableArrayList(cargar());
    }

    public ObservableList<Libro> getLibros() {
        return libros;
    }

    private List<Libro> cargar() {
        List<Libro> listaLibros = new ArrayList<>();

        if (!editoriales.isEmpty()) {
            for (Map.Entry<LibroDTO, String> entry : editoriales.entrySet()) {
                listaLibros.add(Libro.fromDTO(entry.getKey()));
            }
        }
        return listaLibros;
    }
    public void agregarLibro(Libro libro) throws LibroException {
        validarLibro(libro);
        libros.add(libro);
        editoriales.put(libro.toDTO(), "Santillana");
        guardar();
    }

    public void eliminarLibro(Libro libro) throws LibroException {

        if (libro == null) {
            throw new LibroException("Debe seleccionar un libro");
        }
        libros.remove(libro);
        editoriales.remove(libro.toDTO());
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
        if (libro.getFechaPublicacion().isAfter(LocalDate.now())) {
            throw new LibroException("La fecha no puede ser superior a la de hoy");
        }

        boolean existe = libros.stream()
                .anyMatch(l -> l.getTitulo().equalsIgnoreCase(libro.getTitulo()));

        if (existe) {
            throw new LibroException("Ya existe un libro con ese titulo");
        }
    }

    private void guardar() {
        repository.guardar(editoriales);
    }

    public void actualizarPrecio(Libro libro, double nuevoPrecio) throws LibroException {
        if (nuevoPrecio <= 0) {
            throw new LibroException("El precio debe ser mayor que 0");
        }
        LibroDTO viejoDTO = libro.toDTO();
        String editorial = editoriales.get(viejoDTO);
        libro.setPrecio(nuevoPrecio);
        editoriales.remove(viejoDTO);
        editoriales.put(libro.toDTO(), editorial);
        guardar();
    }

    public void actualizarStock(Libro libro, int nuevoStock) throws LibroException {
        if (nuevoStock < 0) {
            throw new LibroException("El stock no puede ser negativo");
        }
        LibroDTO viejoDTO = libro.toDTO();
        String editorial = editoriales.get(viejoDTO);
        libro.setStock(nuevoStock);
        editoriales.remove(viejoDTO);
        editoriales.put(libro.toDTO(), editorial);
        guardar();
    }

    public void actualizarFechaPublicacion(Libro libro, LocalDate fechaPublicacion) throws LibroException{
        if (fechaPublicacion.isAfter(LocalDate.now())) {
            throw new LibroException("La fecha no puede ser superior a la de hoy");
        }
        LibroDTO viejoDTO = libro.toDTO();
        String editorial = editoriales.get(viejoDTO);
        libro.setFechaPublicacion(fechaPublicacion);
        editoriales.remove(viejoDTO);
        editoriales.put(libro.toDTO(), editorial);
        guardar();
    }
}
