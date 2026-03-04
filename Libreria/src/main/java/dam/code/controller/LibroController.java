package dam.code.controller;

import dam.code.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LibroController {

    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro,String> colTitulo;
    @FXML private TableColumn<Libro,String> colAutor;
    @FXML private TableColumn<Libro,Double> colPrecio;
    @FXML private TableColumn<Libro,Integer> colStock;

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;

    private ObservableList<Libro> listaLibros;

    @FXML
    private void initialize() {

        listaLibros = FXCollections.observableArrayList();

        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        colStock.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());

        tablaLibros.setItems(listaLibros);

        //Datos iniciales
        listaLibros.addAll(
                new Libro("El Quijote", "Cervantes", 19.99, 5),
                new Libro("1984", "Orwell", 14.50, 8),
                new Libro("Clean Code", "Robert C. Martin", 35.00, 3)
        );
    }

    @FXML
    private void addLibro() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            if (titulo.isEmpty() || autor.isEmpty() || precio < 0 || stock < 0) {
                mostrarError("Titulo y autor son obligatorios, además el precio y el stock no puede ser negativo.");
                return;
            }

            Libro nuevo = new Libro(titulo, autor, precio, stock);
            listaLibros.add(nuevo);

            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarError("Precio y stock deben ser números válidos.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Datos incorrectos");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtPrecio.clear();
        txtStock.clear();
    }

    @FXML
    private void eliminarLibro() {

        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            listaLibros.remove(seleccionado);
        }

    }
}
