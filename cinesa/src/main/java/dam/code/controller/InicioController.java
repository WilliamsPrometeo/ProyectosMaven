package dam.code.controller;

import dam.code.exception.PersonaException;
import dam.code.models.Persona;
import dam.code.persistence.JsonManager;
import dam.code.service.PeliculaService;
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

import java.io.IOException;

public class InicioController {

    @FXML private TextField txtDni;
    @FXML private PasswordField txtPassword;

    @FXML private Label lblMensaje;

    private RegistroService service;

    public void setRegistroService(RegistroService service) {
        this.service = service;
    }

    @FXML
    public void iniciarSesion(){
        if (!validarCampos()) {
            lblMensaje.setText("Todos los campos son obligatorios");
            lblMensaje.setStyle("-fx-text-fill: red");
            return;
        }
        String dni = txtDni.getText();
        String password = txtPassword.getText();
        try {

            Persona usuario = service.comprobarCredenciales(dni, password);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/peliculas_view.fxml"));
            Parent root = loader.load();

            PeliculaService peliculaService = new PeliculaService(new JsonManager(usuario.getDni()));

            PeliculaController controller = loader.getController();
            controller.setPeliculaService(peliculaService);
            controller.setUsuario(usuario);

            Stage stage = (Stage) txtDni.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (PersonaException | IOException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    public void registro() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registro_view.fxml"));
            Parent root = loader.load();

            RegistroController controller = loader.getController();
            controller.setRegistroService(service);

            Stage stage = (Stage) txtDni.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private boolean validarCampos() {
        return !txtDni.getText().isEmpty() && !txtPassword.getText().isEmpty();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
