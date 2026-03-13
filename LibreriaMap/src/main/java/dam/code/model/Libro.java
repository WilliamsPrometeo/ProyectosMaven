package dam.code.model;

import dam.code.dto.LibroDTO;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Libro {

    private final StringProperty titulo;
    private final StringProperty autor;
    private final DoubleProperty precio;
    private final IntegerProperty stock;
    private final ObjectProperty<LocalDate> fechaPublicacion;

    public Libro(String titulo, String autor, double precio, int stock, LocalDate fechaPublicacion) {
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.precio = new SimpleDoubleProperty(precio);
        this.stock = new SimpleIntegerProperty(stock);
        this.fechaPublicacion = new SimpleObjectProperty<>(fechaPublicacion);
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
    public LocalDate getFechaPublicacion() {
        return fechaPublicacion.get();
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
    public ObjectProperty<LocalDate> fechaPublicacionProperty() {
        return fechaPublicacion;
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion.set(fechaPublicacion);
    }

    public LibroDTO toDTO() {
        return new LibroDTO(
                getTitulo(),
                getAutor(),
                getPrecio(),
                getStock(),
                getFechaPublicacion()
        );
    }

    public static Libro fromDTO(LibroDTO dto) {
        return new Libro(
                dto.getTitulo(),
                dto.getAutor(),
                dto.getPrecio(),
                dto.getStock(),
                dto.getFechaPublicacion()
        );
    }
}
