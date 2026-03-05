package dam.code;

import dam.code.controller.LibroController;
import dam.code.persistence.JsonManager;
import dam.code.repository.LibroRepository;
import dam.code.service.LibroService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLibreria extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/libreria_view.fxml"));

        Parent root = fxmlLoader.load();
        LibroController controller = fxmlLoader.getController();

        LibroRepository repository = new JsonManager();
        LibroService service = new LibroService(repository);

        controller.setLibroService(service);

        stage.setScene(new Scene(root, 800, 600));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
