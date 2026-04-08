package dam.code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JDBCMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UsuarioView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);

        stage.setScene(scene);
        stage.setTitle("Gestión de Usuarios");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
