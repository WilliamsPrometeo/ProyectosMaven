package dam.code.dto;

import java.time.LocalDate;

public class PeliculaDTO {
    private String id;
    private String titulo;
    private String director;
    private int duracion;
    private LocalDate fechaPublicacion;

    public PeliculaDTO(String id, String titulo, String director, int duracion, LocalDate fechaPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public int getDuracion() {
        return duracion;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PeliculaDTO p = (PeliculaDTO) obj;
        return this.id != null ? this.id.equals(p.id) : p.id == null;
    }

    @Override
    public String toString() {
        return String.format("Pelicula: %s - Titulo: %s - Director: %s - Duración: %d - Fecha publicación: %s",
                id,
                titulo,
                director,
                duracion,
                fechaPublicacion.toString());
    }
}
