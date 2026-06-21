import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.dashboard;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        dashboard root = new dashboard();
        Scene scene = new Scene(root.getView(), 1100, 650);
        stage.setTitle("Gestion de Bibliothèque");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.show();
    }
}
