package dam.code.controller;

import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;
import dam.code.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPasswordRepit;

    @FXML private Label lblMensaje;

    private UsuarioService service;

    public void setUsuarioServices(UsuarioService service) {
        this.service = service;
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
                        txtEmail.getText()
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
            stage.setWidth(800);
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
                && !txtPasswordRepit.getText().isEmpty();
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

