package pages.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.MainApp;
import tools.actions.Actions;
import tools.windows.OpenNewWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentWindowController {

    OpenNewWindow newWindow = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button englishLangButton;

    @FXML
    private Button backButton;

    @FXML
    private Button russianLangButton;

    @FXML
    void initialize() {
        assert englishLangButton != null : "fx:id=\"englishLangButton\" was not injected: check your FXML file 'studentWindow.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'studentWindow.fxml'.";

        assert russianLangButton != null : "fx:id=\"russianLangButton\" was not injected: check your FXML file 'studentWindow.fxml'.";
        backButton.setOnAction(event -> {
            String windowLocation = "/fxml/open.fxml";
            try {
                newWindow.openNewScene(windowLocation, MainApp.getStage());
            }
            catch (Exception e){
                System.out.println("Что-то пошло не так открытием страницы: " + windowLocation);
            }
        });

        englishLangButton.setOnAction(event -> Actions.goTo(newWindow, "/fxml/studyWindow.fxml"));

        russianLangButton.setOnAction(event -> Actions.goTo(newWindow, "/fxml/studyWindow.fxml"));

    }

}
