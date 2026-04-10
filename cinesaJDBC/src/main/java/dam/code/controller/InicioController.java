package dam.code.controller;

import dam.code.exceptions.PeliculaException;
import dam.code.exceptions.UsuarioException;
import dam.code.models.Usuario;
import dam.code.service.PeliculaService;
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

import java.io.IOException;

public class InicioController {

    @FXML private TextField txtDni;
    @FXML private PasswordField txtPassword;

    @FXML private Label lblMensaje;

    private UsuarioService service;

    public void setUsuarioService(UsuarioService service) {
        this.service = service;
    }

    @FXML
    public void iniciarSesion(){
        if(!validarCampos()) {
            lblMensaje.setText("Todos los campos son obligatorios");
            lblMensaje.setStyle("-fx-text-fill: red");
            return;
        }
        String dni = txtDni.getText();
        String password = txtPassword.getText();
        try {
            Usuario usuario = service.login(dni, password);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Visualizaciones_view.fxml"));
            Parent root = loader.load();
            VisualizacionController controller = loader.getController();
            controller.setUsuario(usuario);
            controller.setPeliculaService(new PeliculaService());

            Stage stage = (Stage) txtDni.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(new Scene(root));
        } catch (PeliculaException | IOException | UsuarioException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    public void registro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Registro_view.fxml"));
            Parent root = loader.load();
            RegistroController controller = loader.getController();
            controller.setUsuarioServices(service);
            Stage stage = (Stage) txtDni.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validarCampos() {
        return !txtDni.getText().isEmpty() && !txtPassword.getText().isEmpty();
    }
}
