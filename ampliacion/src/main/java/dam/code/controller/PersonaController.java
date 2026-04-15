package dam.code.controller;

import dam.code.model.Persona;
import dam.code.service.PersonaService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PersonaController {

    private final PersonaService service = new PersonaService();

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;

    @FXML private TableView<Persona> tablaPersonas;
    @FXML private TableColumn<Persona, String> colNombre;
    @FXML private TableColumn<Persona, String> colApellido;

    @FXML
    private void initialize(){
        prefWidthColumns();
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tablaPersonas.setItems(service.getPersonas());
    }

    private void prefWidthColumns() {
        tablaPersonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colNombre.prefWidthProperty().bind(tablaPersonas.widthProperty().multiply(0.5));
        colApellido.prefWidthProperty().bind(tablaPersonas.widthProperty().multiply(0.5));
    }

    @FXML
    public void addPersona() {
        Persona persona = new Persona(
                txtNombre.getText(),
                txtApellido.getText()
        );
        service.registrar(persona);
        limpiarCampos();
        tablaPersonas.setItems(service.getPersonas());
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
    }
}
