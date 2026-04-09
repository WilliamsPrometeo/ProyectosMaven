package dam.code.models;

import java.time.LocalDate;

public class Pelicula {
    private int id;
    private String titulo;
    private String director;
    private int duracion;
    private LocalDate fecha_publicacion;

    public Pelicula(String titulo, String director, int duracion, LocalDate fecha_publicacion) {
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.fecha_publicacion = fecha_publicacion;
    }

    public Pelicula(int id, String titulo, String director, int duracion, LocalDate fecha_publicacion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.fecha_publicacion = fecha_publicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(LocalDate fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }
}
