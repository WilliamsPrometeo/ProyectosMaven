package dam.code;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FormController {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMensaje;

    @FXML
    public void registrarUsuario() {
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios");
            lblMensaje.setStyle("-fx-text-fill: red");
        } else {
            lblMensaje.setText("Usuario registrado correctamente");
            lblMensaje.setStyle("-fx-text-fill: lightgreen");
        }
    }
}
