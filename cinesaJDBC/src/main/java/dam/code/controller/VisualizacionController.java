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

public class VisualizacionController {

    private Usuario usuario;
    private PeliculaService service;

    @FXML private TableView<Pelicula> tablaVisualizaciones;
    @FXML private TableColumn<Pelicula, Integer> colId;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, Integer> colDuracion;
    @FXML private TableColumn<Pelicula, LocalDate> colFecha;
    @FXML private TableColumn<Pelicula, Integer> colVisualizaciones;

    @FXML private Label lblUsuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblUsuario.setText("Usuario: " + usuario.getNombre());
    }

    public void setPeliculaService(PeliculaService service) throws PeliculaException {
        this.service = service;

        tablaVisualizaciones.setItems(service.obtenerPeliculasPorUsuario(usuario.getId()));

    }

    @FXML
    private void initialize() {
        prefWidthColumns();

        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colDirector.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDirector()));
        colDuracion.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDuracion()).asObject());
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha_publicacion()));
        colVisualizaciones.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getVisualizaciones()).asObject());

    }
    private void prefWidthColumns() {
        tablaVisualizaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colId.prefWidthProperty().bind(tablaVisualizaciones.widthProperty().multiply(0.05));
        colTitulo.prefWidthProperty().bind(tablaVisualizaciones.widthProperty().multiply(0.35));
        colDirector.prefWidthProperty().bind(tablaVisualizaciones.widthProperty().multiply(0.30));
        colDuracion.prefWidthProperty().bind(tablaVisualizaciones.widthProperty().multiply(0.10));
        colFecha.prefWidthProperty().bind(tablaVisualizaciones.widthProperty().multiply(0.15));
        colVisualizaciones.prefWidthProperty().bind(tablaVisualizaciones.widthProperty().multiply(0.05));
    }

    private void setVisualizacion() {
        tablaVisualizaciones.setRowFactory(tv -> {
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
                                tablaVisualizaciones.setItems(service.obtenerPeliculasPorUsuario(usuario.getId()));
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
    public void peliculas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Peliculas_view.fxml"));

            Parent root = loader.load();

            PeliculaController controller = loader.getController();
            controller.setUsuario(usuario);
            controller.setPeliculaService(service);

            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
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

                    Stage stage = (Stage) tablaVisualizaciones.getScene().getWindow();
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
}
