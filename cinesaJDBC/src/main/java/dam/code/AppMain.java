package dam.code;

import dam.code.controller.InicioController;
import dam.code.dao.UsuarioDAO;
import dam.code.dao.impl.UsuarioDAOImpl;
import dam.code.service.UsuarioService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        UsuarioService service = new UsuarioService();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Inicio_view.fxml"));

        Parent root = loader.load();

        InicioController controller = loader.getController();
        controller.setUsuarioService(service);

        stage.setScene(new Scene(root));
        stage.setTitle("Cinesa");
        stage.setResizable(false);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
