package dam.code.controller;

import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;
import dam.code.models.utils.Rol;
import dam.code.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPasswordRepit;

    @FXML private ComboBox<Rol> comboRol;

    @FXML private Label lblMensaje;

    private UsuarioService service;

    public void setUsuarioServices(UsuarioService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        comboRol.setItems(FXCollections.observableArrayList(Rol.values()));
    }

    @FXML
    private void registrarUsuario() {
        if (validarPassword()) {
            try {
                if(!validarCampos()) {
                    lblMensaje.setText("Todos los campos son obligatorios");
                    lblMensaje.setStyle("-fx-text-fill: red;");
                    return;
                }
                Usuario usuario = new Usuario(
                        txtDni.getText(),
                        txtNombre.getText(),
                        txtEmail.getText(),
                        comboRol.getValue()
                );
                service.registrar(usuario, txtPassword.getText());
                lblMensaje.setText("Usuario registrado con exito");
                lblMensaje.setStyle("-fx-text-fill: lightgreen;");
                limpiarCampos();
            } catch (UsuarioException e) {
                mostrarError(e.getMessage());
            }
        } else {
            lblMensaje.setText("Las contraseñas no coinciden");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }

    }

    private void limpiarCampos() {
        txtDni.clear();
        txtNombre.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtPasswordRepit.clear();
        comboRol.setValue(null);
    }

    @FXML
    public void inicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Inicio_view.fxml"));

            Parent root = loader.load();

            InicioController controller = loader.getController();
            controller.setUsuarioService(service);

            Stage stage = (Stage) txtDni.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(400);
            stage.setHeight(600);
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private boolean validarCampos() {
        return !txtDni.getText().isEmpty()
                && !txtNombre.getText().isEmpty()
                && !txtEmail.getText().isEmpty()
                && !txtPassword.getText().isEmpty()
                && !txtPasswordRepit.getText().isEmpty()
                && comboRol.getValue() != null;
    }

    private boolean validarPassword() {
        return txtPasswordRepit.getText().equals(txtPassword.getText());
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

