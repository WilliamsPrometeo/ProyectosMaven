package dam.code.model;

public class Videojuego {

    private String titulo;
    private int pegi;

    public Videojuego(String titulo, int pegi) {
        this.titulo = titulo;
        this.pegi = pegi;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPegi() {
        return pegi;
    }

    public void setPegi(int pegi) {
        this.pegi = pegi;
    }
}
