package dam.code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLibreria extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/libreria_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("LIBRERIA PROMETEO");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
