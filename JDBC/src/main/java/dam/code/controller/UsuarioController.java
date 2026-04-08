package dam.code.controller;

import dam.code.model.Usuario;
import dam.code.service.UsuarioService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsuarioController {

    private final UsuarioService service = new UsuarioService();

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colEmail;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        cargarUsuarios();
    }

    @FXML
    public void guardarUsuario() {
        try {
            service.crearUsuario(txtNombre.getText(), txtEmail.getText());
            limpiarCampos();
            cargarUsuarios();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cargarUsuarios() {
        tablaUsuarios.setItems(FXCollections.observableArrayList(service.listarUsuarios()));
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtEmail.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.show();
    }
}
