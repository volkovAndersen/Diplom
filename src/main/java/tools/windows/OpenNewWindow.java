package tools.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenNewWindow {

    public void openNewScene(String window, Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(window));
        stage.setTitle("Интерактивное обучающее приложение для детей дошкольного и школьного возраста на основе распознавания образов");
        stage.setScene(new Scene(root, 1000, 600));
        stage.toFront();
        stage.setResizable(false);
        stage.show();
    }

    public void openNewWindow (String window, Stage stage) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();

        stage.setScene(new Scene(root, 1000, 600));
        stage.setResizable(false);
        stage.showAndWait();
    }
}