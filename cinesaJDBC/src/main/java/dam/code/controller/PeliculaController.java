package dam.code.controller;

import dam.code.exceptions.PeliculaException;
import dam.code.models.Pelicula;
import dam.code.models.Usuario;
import dam.code.service.PeliculaService;
import dam.code.service.UsuarioService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PeliculaController {

    private Usuario usuario;
    private PeliculaService service;

    @FXML private Label lblUsuario;

    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private TextField txtDuracion;
    @FXML private DatePicker txtFecha;

    @FXML private TableView<Pelicula> tablaPeliculas;
    @FXML private TableColumn<Pelicula, Integer> colId;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, Integer> colDuracion;
    @FXML private TableColumn<Pelicula, LocalDate> colFecha;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblUsuario.setText("Usuario:" + usuario.getNombre());
    }

    public void setPeliculaService(PeliculaService service) throws PeliculaException {
        this.service = service;
        tablaPeliculas.setItems(service.obtenerPeliculas());
    }

    @FXML
    private void initialize() {
        prefWidthColumns();
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colDirector.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDirector()));
        colDuracion.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDuracion()).asObject());
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha_publicacion()));

        txtFecha.setEditable(false);
        validarPicker();
    }

    private void validarPicker() {
        txtFecha.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d0d0d0;");
                }
            }
        });
    }

    private void prefWidthColumns() {
        tablaPeliculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colId.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.05));
        colTitulo.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.35));
        colDirector.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.30));
        colDuracion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.15));
        colFecha.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.15));

        setVisualizacion();
    }

    private void setVisualizacion() {
        tablaPeliculas.setRowFactory(tv -> {
            TableRow<Pelicula> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Pelicula pelicula = row.getItem();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Visualización");
                    alert.setHeaderText("Añadir visualización");
                    alert.setContentText("¿Quieres añadir una visualización a " + pelicula.getTitulo() + "?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                service.addVisualizacion(usuario.getId(), pelicula);
                                tablaPeliculas.setItems(service.obtenerPeliculas());
                            } catch (PeliculaException e) {
                                mostrarError(e.getMessage());
                            }
                        }
                    });
                }
            });
            return row;
        });
    }

    @FXML
    public void addPelicula() {
        try {
            if (!validarCampos()) throw new PeliculaException("Todos los campos son obigatorios");
            Pelicula pelicula = new Pelicula(
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    LocalDate.parse(txtFecha.getValue().toString())
            );
            service.agregarPelicula(pelicula);
            tablaPeliculas.setItems(service.obtenerPeliculas());
            limpiarCampos();
        } catch (PeliculaException | DateTimeParseException e) {
            mostrarError(e.getMessage());
        } catch (NumberFormatException e) {
            mostrarError("La duración tiene que ser un número válido.");
        }
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtDirector.clear();
        txtDuracion.clear();
        txtFecha.setValue(null);
    }

    private boolean validarCampos() {
        return !txtTitulo.getText().isBlank()
                && !txtDirector.getText().isBlank()
                && !txtDuracion.getText().isBlank()
                && txtFecha.getValue() != null;
    }

    @FXML
    public void visualizaciones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Visualizaciones_view.fxml"));
            Parent root = loader.load();
            VisualizacionController controller = loader.getController();
            controller.setUsuario(usuario);
            controller.setPeliculaService(service);

            Stage stage = (Stage) txtTitulo.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    public void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesion");
        alert.setHeaderText("¿Seguro que desea cerrar sesion?");
        alert.setContentText("Se cerrará la sesión actual");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Inicio_view.fxml"));
                    Parent root = loader.load();
                    InicioController controller = loader.getController();
                    controller.setUsuarioService(new UsuarioService());

                    Stage stage = (Stage) txtTitulo.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setWidth(800);
                    stage.setHeight(600);
                    stage.setScene(new Scene(root));
                } catch (Exception e) {
                    mostrarError(e.getMessage());
                }
            }
        });
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
