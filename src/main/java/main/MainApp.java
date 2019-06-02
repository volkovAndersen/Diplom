package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class MainApp extends Application {

    private static Stage stage;

    /**
     * Определение окна приложения
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/open.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.toFront();
        primaryStage.setResizable(false);
        primaryStage.show();
        stage = primaryStage;
    }

    /**
     *
     * @return передача Stage главного окна приложения
     */
    public static Stage getStage(){
        return stage;
    }

    /**
     * Вход в программу
     * @param args
     */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
}
