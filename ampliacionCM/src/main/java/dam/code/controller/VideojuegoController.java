package dam.code.controller;

import dam.code.model.Videojuego;
import dam.code.service.VideojuegoService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VideojuegoController {

    private final VideojuegoService service = new VideojuegoService();

    @FXML private TextField txtTitulo;
    @FXML private TextField txtPegi;

    @FXML private TableView<Videojuego> tablaVideojuego;
    @FXML private TableColumn<Videojuego, String> colTitulo;
    @FXML private TableColumn<Videojuego, Integer> colPegi;

    @FXML
    private void initialize() {
        prefWidthColumns();
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colPegi.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPegi()).asObject());

        tablaVideojuego.setItems(service.obtenerData());
    }

    private void prefWidthColumns() {
        tablaVideojuego.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colTitulo.prefWidthProperty().bind(tablaVideojuego.widthProperty().multiply(0.8));
        colPegi.prefWidthProperty().bind(tablaVideojuego.widthProperty().multiply(0.2));
    }

    @FXML
    public void sendData() {
        try {
            String titulo = txtTitulo.getText();
            int pegi = Integer.parseInt(txtPegi.getText());

            service.sendData(new Videojuego(titulo, pegi));
            tablaVideojuego.setItems(service.obtenerData());
            limpiarCampos();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtPegi.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
