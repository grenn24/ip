package diheng;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private DiHeng chatbot = new DiHeng();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setChatbot(chatbot);  // inject the chatbot instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
