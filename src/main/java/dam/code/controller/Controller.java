package dam.code.controller;

import dam.code.model.Entrada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField edadField;

    @FXML
    private ListView<Entrada> listaEntradas;

    private ObservableList<Entrada> entradas = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        listaEntradas.setItems(entradas);

        listaEntradas.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                Entrada entradaSeleccionada = listaEntradas.getSelectionModel().getSelectedItem();
                if (entradaSeleccionada != null) {
                    entradas.remove(entradaSeleccionada);
                }
            }
        });
    }

    @FXML
    private void addEntrada() {
        String nombre = nombreField.getText();
        int edad = Integer.parseInt(edadField.getText());

        if (!nombre.isEmpty() && !edadField.getText().isEmpty()) {
            Entrada entrada = new Entrada(nombre, edad);
            entradas.add(entrada);

            nombreField.setText("");
            edadField.setText("");
        }
    }
}
