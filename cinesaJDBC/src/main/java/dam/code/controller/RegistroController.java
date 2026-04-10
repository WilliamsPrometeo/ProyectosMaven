package dam.code.controller;

import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;
import dam.code.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    private boolean validarCampos() {
        return false;
    }

    private boolean validarPassword() {
        return false;
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

