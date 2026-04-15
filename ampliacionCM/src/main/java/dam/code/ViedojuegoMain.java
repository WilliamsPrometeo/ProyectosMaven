package dam.code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViedojuegoMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pricipal_view.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.setTitle("FXML Básico");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
