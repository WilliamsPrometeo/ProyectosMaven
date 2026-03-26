package dam.code;

import dam.code.controller.InicioController;
import dam.code.controller.RegistroController;
import dam.code.persistence.RegistroDAO;
import dam.code.service.RegistroService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppCinesa extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        RegistroDAO registroDAO = new RegistroDAO();
        RegistroService registroService = new RegistroService(registroDAO);

        FXMLLoader loader;

        if (registroService.getRegistros().isEmpty()) {
            loader = new FXMLLoader(getClass().getResource("/view/registro_view.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/view/inicio_view.fxml"));
        }

        Parent root = loader.load();

        if (loader.getController() instanceof RegistroController controller) {
            controller.setRegistroService(registroService);
        }

        if (loader.getController() instanceof InicioController controller) {
            controller.setRegistroService(registroService);
        }

        stage.setScene(new Scene(root));
        stage.setTitle("Cinesa");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
