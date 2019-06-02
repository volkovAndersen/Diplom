package pages.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.MainApp;
import tools.actions.Actions;
import tools.windows.OpenNewWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherWindowController {

    OpenNewWindow newWindow = new OpenNewWindow();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'teacherWindow.fxml'.";

        backButton.setOnAction(event -> Actions.goTo(newWindow, "/fxml/open.fxml"));
    }
}
