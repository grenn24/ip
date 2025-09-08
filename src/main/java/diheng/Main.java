package diheng;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for DiHeng chatbot using FXML.
 */
public class Main extends Application {
    /**
     * The chatbot instance.
     */
    private DiHeng chatbot = new DiHeng();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();

            Scene scene = new Scene(root);

            // Apply CSS for modern styling
            scene.getStylesheets().add(Main.class.getResource("/css/main.css").toExternalForm());
            scene.getStylesheets().add(Main.class.getResource("/css/dialog-box.css").toExternalForm());

            // Inject the chatbot instance into the controller
            fxmlLoader.<MainWindow>getController().setChatbot(chatbot);

            stage.setScene(scene);
            stage.setTitle("DiHeng Chat");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
