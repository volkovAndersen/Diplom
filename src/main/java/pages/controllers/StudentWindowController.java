package pages.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.MainApp;
import tools.actions.Actions;
import tools.utils.LanguageTesseract;
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

        backButton.setOnAction(event -> Actions.goTo(newWindow,"/fxml/open.fxml"));

        englishLangButton.setOnAction(event -> {
            LanguageTesseract.setLang("ENG");
            Actions.goTo(newWindow, "/fxml/studyWindow.fxml");
        });

        russianLangButton.setOnAction(event -> {
            LanguageTesseract.setLang("RUS");
            Actions.goTo(newWindow, "/fxml/studyWindow.fxml");
        });
    }
}
