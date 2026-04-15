package dam.code.service;

import dam.code.dao.PeliculaDAO;
import dam.code.dao.impl.PeliculaDAOImpl;
import dam.code.exceptions.PeliculaException;
import dam.code.models.Pelicula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.time.LocalDate;

public class PeliculaService {

    private final PeliculaDAO peliculaDAO = new PeliculaDAOImpl();

    public ObservableList<Pelicula> obtenerPeliculas() throws PeliculaException {
        return FXCollections.observableArrayList(peliculaDAO.listar());
    }

    public void addVisualizacion(int idUsuario, Pelicula pelicula) throws PeliculaException {
        peliculaDAO.visualizar(idUsuario, pelicula.getId());
    }

    public void agregarPelicula(Pelicula pelicula) throws PeliculaException {
        validarPelicula(pelicula);

        peliculaDAO.registrar(pelicula);
    }

    private void validarPelicula(Pelicula pelicula) throws PeliculaException {
        if (pelicula.getDirector().length() < 2) {
            throw new PeliculaException("El nombre del director es muy corto");
        }
        if (pelicula.getDuracion() > 500) {
            throw new PeliculaException("Duración poco realista");
        }
        if (pelicula.getFecha_publicacion().isBefore(LocalDate.of(1895,12,28))) {
            throw new PeliculaException("La fecha de publicación no puede ser inferior a la creación del cine");
        }
    }

    public ObservableList<Pelicula> obtenerPeliculasPorUsuario(int idUsuario) throws PeliculaException {
        return FXCollections.observableArrayList(peliculaDAO.obtenerPeliculasPorUsuario(idUsuario));
    }
}
