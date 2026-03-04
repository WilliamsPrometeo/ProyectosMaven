package dam.code.model;

import dam.code.dto.LibroDTO;
import javafx.beans.property.*;

public class Libro {

    private final StringProperty titulo;
    private final StringProperty autor;
    private final DoubleProperty precio;
    private final IntegerProperty stock;

    public Libro(String titulo, String autor, double precio, int stock) {
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.precio = new SimpleDoubleProperty(precio);
        this.stock = new SimpleIntegerProperty(stock);
    }

    // Getters normales
    public String getTitulo() {
        return titulo.get();
    }

    public String getAutor() {
        return autor.get();
    }

    public double getPrecio() {
        return precio.get();
    }

    public int getStock() {
        return stock.get();
    }

    // Getters Properties (Claves para TableView)
    public StringProperty tituloProperty() {
        return titulo;
    }

    public StringProperty autorProperty() {
        return autor;
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public LibroDTO toDTO() {
        return new LibroDTO(
                getTitulo(),
                getAutor(),
                getPrecio(),
                getStock()
        );
    }

    public static Libro fromDTO(LibroDTO dto) {
        return new Libro(
                dto.getTitulo(),
                dto.getAutor(),
                dto.getPrecio(),
                dto.getStock()
        );
    }
}
