package dam.code.controller;

import dam.code.exception.PersonaException;
import dam.code.models.Persona;
import dam.code.service.RegistroService;
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
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPasswordRepit;

    @FXML private Label lblMensaje;

    private RegistroService service;

    public void setRegistroService(RegistroService service) {
        this.service = service;
    }

    @FXML
    public void registrarUsuario() {
        if (validarPassword()) {
            try {
                if (!validarCampos()) {
                    lblMensaje.setText("Todos los campos son obligatorios");
                    lblMensaje.setStyle("-fx-text-fill: red");
                    return;
                }
                Persona persona = new Persona(
                        txtDni.getText(),
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtEmail.getText()
                );
                service.registrarUsuario(persona, txtPassword.getText());
                lblMensaje.setText("Usuario registrado correctamente");
                lblMensaje.setStyle("-fx-text-fill: lightgreen");
                limpiarCampos();
            } catch (PersonaException e) {
                mostrarError(e.getMessage());
            }
        } else {
            lblMensaje.setText("Las contraseñas no coinciden");
            lblMensaje.setStyle("-fx-text-fill: red");
        }
    }

    private void limpiarCampos() {
        txtDni.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtPasswordRepit.clear();
    }

    @FXML
    public void inicio() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inicio_view.fxml"));
            Parent root = loader.load();

            InicioController controller = loader.getController();
            controller.setRegistroService(service);

            Stage stage = (Stage) txtDni.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private boolean validarCampos() {
        return !txtDni.getText().isEmpty()
                && !txtNombre.getText().isEmpty()
                && !txtApellido.getText().isEmpty()
                && !txtEmail.getText().isEmpty()
                && !txtPassword.getText().isEmpty()
                && !txtPasswordRepit.getText().isEmpty();
    }

    private boolean validarPassword() {
        return txtPassword.getText().equals(txtPasswordRepit.getText());
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
