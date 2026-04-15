package dam.code.service;

import dam.code.model.Videojuego;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class VideojuegoService {

    private ObservableList<Videojuego> listaVideojuegos = FXCollections.observableArrayList(new ArrayList<>());

    public void sendData(Videojuego videojuego) {
        listaVideojuegos.add(videojuego);
    }

    public ObservableList<Videojuego> obtenerData() {
        return listaVideojuegos;
    }
}
