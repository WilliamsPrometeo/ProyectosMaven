package dam.code.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VideojuegoController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private Label lblMensaje;

    @FXML
    public void sendName() {
        try {
            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());

            lblMensaje.setText("Hola " + nombre + " tienes " + edad + " años.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
