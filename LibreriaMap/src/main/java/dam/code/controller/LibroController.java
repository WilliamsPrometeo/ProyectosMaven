package dam.code.controller;

import dam.code.exceptions.LibroException;
import dam.code.model.Libro;
import dam.code.service.LibroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LibroController {

    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro,String> colTitulo;
    @FXML private TableColumn<Libro,String> colAutor;
    @FXML private TableColumn<Libro,Double> colPrecio;
    @FXML private TableColumn<Libro,Integer> colStock;
    @FXML private TableColumn<Libro, LocalDate> colFechaPublicacion;

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtFechaPublicacion;

    private LibroService libroService;

    public void setLibroService(LibroService libroService) {
        this.libroService = libroService;
        tablaLibros.setItems(libroService.getLibros());
    }

    @FXML
    private void initialize() {
        prefWidthColumns();

        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        colStock.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        colFechaPublicacion.setCellValueFactory(cellData -> cellData.getValue().fechaPublicacionProperty());

        tablaLibros.setEditable(true);

        //Precio editable
        colPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecio.setOnEditCommit(event -> {
            Libro libro = event.getRowValue();
            double nuevoPrecio = event.getNewValue();
            try {
                libroService.actualizarPrecio(libro, nuevoPrecio);
            } catch (LibroException e) {
                mostrarError(e.getMessage());
            }
        });

        //Stock editable
        colStock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colStock.setOnEditCommit(event -> {
            Libro libro = event.getRowValue();
            int nuevoStock = event.getNewValue();
            try {
                libroService.actualizarStock(libro, nuevoStock);
            } catch (LibroException e) {
                mostrarError(e.getMessage());
            }
        });

        //Fecha Publicacion editable
        colFechaPublicacion.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        colFechaPublicacion.setOnEditCommit(event -> {
            Libro libro = event.getRowValue();
            LocalDate fechaPublicacion = event.getNewValue();
            try {
                libroService.actualizarFechaPublicacion(libro, fechaPublicacion);
            } catch (LibroException e) {
                mostrarError(e.getMessage());
            }
        });

    }

    private void prefWidthColumns() {
        tablaLibros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colTitulo.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.40));
        colAutor.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.25));
        colPrecio.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.10));
        colStock.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.10));
        colFechaPublicacion.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.15));
    }

    @FXML
    private void addLibro() {
        try {
            Libro libro = new Libro(
                    txtTitulo.getText(),
                    txtAutor.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText()),
                    LocalDate.parse(txtFechaPublicacion.getText())
            );

            libroService.agregarLibro(libro);

            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarError("Precio y stock deben ser números válidos.");
        } catch (LibroException | DateTimeParseException e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Datos incorrectos");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtFechaPublicacion.clear();
    }

    @FXML
    private void eliminarLibro() {

        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                libroService.eliminarLibro(seleccionado);
            } catch (LibroException e) {
                mostrarError(e.getMessage());
            }
        }
    }
}
