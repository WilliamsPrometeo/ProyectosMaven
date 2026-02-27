package dam.code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

        if(!validarPassword(password)) {
            mostrarAlertPassword();
            return;
        }

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios");
            lblMensaje.setStyle("-fx-text-fill: red");
        } else {
            lblMensaje.setText("Usuario registrado correctamente");
            lblMensaje.setStyle("-fx-text-fill: lightgreen");
            mostrarAlertConfirm();
        }
    }

    private boolean validarPassword(String password) {
        if (password.length() < 6) return false;

        return password.matches(".*\\d.*");
    }

    private void mostrarAlertPassword() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validacion");
        alert.setHeaderText("Contraseña no valida");
        alert.setContentText("La contraseña debe tener mínimo 6 caracteres y al menos un número.");

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        alert.showAndWait();
    }

    private void mostrarAlertConfirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea iniciar sesion?");
        alert.setContentText("Usuario: " + txtNombre.getText());

        ButtonType btnSi = new ButtonType("Si");
        ButtonType btnNo = new ButtonType("No");

        alert.getButtonTypes().setAll(btnSi, btnNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == btnSi) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
                    Parent root = fxmlLoader.load();

                    DashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.setMensajeBienvenida("Bienvenido a nuestra página principal, usuario: " + txtNombre.getText());

                    txtNombre.getScene().setRoot(root);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
