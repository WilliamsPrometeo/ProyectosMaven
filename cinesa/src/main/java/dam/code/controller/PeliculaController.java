package dam.code.controller;

import dam.code.exception.PeliculaException;
import dam.code.models.Pelicula;
import dam.code.models.Persona;
import dam.code.persistence.RegistroDAO;
import dam.code.service.PeliculaService;
import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PeliculaController {

    @FXML private TableView<Pelicula> tablaPeliculas;
    @FXML private TableColumn<Pelicula, String> colId;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, Integer> colDuracion;
    @FXML private TableColumn<Pelicula, LocalDate> colFecha;

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtFecha;

    @FXML private Label lblUsuario;

    public void setUsuario(Persona persona) {
        lblUsuario.setText("Usuario: " + persona.getNombre());
    }

    private PeliculaService service;

    public void setPeliculaService(PeliculaService service) {
        this.service = service;
        tablaPeliculas.setItems(service.getPeliculas());
    }

    @FXML
    private void initialize() {
        prefWidthColumns();

        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colDirector.setCellValueFactory(cellData -> cellData.getValue().directorProperty());
        colDuracion.setCellValueFactory(cellData -> cellData.getValue().duracionProperty().asObject());
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaPublicacionProperty());

        tablaPeliculas.setEditable(true);

        // Titulo editable
        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        colTitulo.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
            String nuevoTitulo= event.getNewValue();
            try {
                service.actualizarTitulo(pelicula, nuevoTitulo);
            } catch (PeliculaException e) {
                mostrarError(e.getMessage());
            }
        });

        colFecha.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        colFecha.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
            LocalDate nuevaFecha = event.getNewValue();
            try {
                service.actualizarFecha(pelicula, nuevaFecha);
            } catch (PeliculaException e) {
                mostrarError(e.getMessage());
            }
        });

        setVisualizacion();
    }

    private void setVisualizacion() {
        tablaPeliculas.setRowFactory(tv -> {
            TableRow<Pelicula> row = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2 && !row.isEmpty()) {

                    Pelicula pelicula = row.getItem();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Visualización");
                    alert.setHeaderText("Añadir visualización");
                    alert.setContentText("¿Quieres añadir una visualización a " + pelicula.getTitulo() + "?");

                    alert.showAndWait().ifPresent(response -> {

                        if (response == ButtonType.OK) {
                            service.addVisualizacion(pelicula);
                        }

                    });

                }

            });

            return row;
        });
    }

    private void prefWidthColumns() {
        tablaPeliculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colId.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.05));
        colTitulo.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.35));
        colDirector.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.30));
        colDuracion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.15));
        colFecha.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.15));

    }
    public void addPelicula() {
        try {
            Pelicula pelicula = new Pelicula(
                    txtId.getText().toUpperCase(),
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    LocalDate.parse(txtFecha.getText())
            );
            service.agregarPelicula(pelicula);
            limpiarCampos();
        } catch (PeliculaException | DateTimeParseException e) {
            mostrarError(e.getMessage());
        } catch (NumberFormatException e) {
            mostrarError("La duración tiene que ser un número válido");
        }
    }

    public void eliminarPelicula() {
        Pelicula seleccionado = tablaPeliculas.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                service.eliminarPelicula(seleccionado);
            } catch (PeliculaException e) {
                mostrarError(e.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtDirector.clear();
        txtDuracion.clear();
        txtFecha.clear();
    }

    public void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesion");
        alert.setHeaderText("¿Seguro que quieres salir?");
        alert.setContentText("Se cerrará la sesión actual.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inicio_view.fxml"));
                    Parent root = loader.load();

                    InicioController controller = loader.getController();
                    controller.setRegistroService(new RegistroService(new RegistroDAO()));

                    Stage stage = (Stage) tablaPeliculas.getScene().getWindow();

                    stage.setScene(new Scene(root));

                } catch (Exception e) {
                    mostrarError(e.getMessage());
                }
            }
        });
    }


}
