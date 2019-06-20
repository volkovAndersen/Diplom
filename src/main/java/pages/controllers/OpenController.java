package pages.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import persons.current.CurrentChild;
import tools.actions.Actions;
import tools.windows.OpenNewWindow;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

/**
 * Контроллер для главного окна прилодения
 */
public class OpenController {

    OpenNewWindow newWindow = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    //кнопка 'Учитель'
    @FXML
    private Button teacherButton;

    //кнопка 'Выход'
    @FXML
    private Button exit;

    //кнопка 'Студент'
    @FXML
    private Button studentButton;

    @FXML
    void initialize() {
        assert teacherButton != null : "fx:id=\"teacherButton\" was not injected: check your FXML file 'open.fxml'.";
        assert exit != null : "fx:id=\"exit\" was not injected: check your FXML file 'open.fxml'.";
        assert studentButton != null : "fx:id=\"studentButton\" was not injected: check your FXML file 'open.fxml'.";

        if (CurrentChild.getIdOfCurrentChild( ) == 1211) {
            studentButton.setDisable(true);
        } else {
            studentButton.setDisable(false);
        }

        /**
         * Перевод в окно 'Учитель'
         */
        teacherButton.setOnAction(event -> Actions.goTo(newWindow, "/fxml/autorisationWindow.fxml"));

        /**
         * Перевод в окно 'Ученик'
         */
        studentButton.setOnAction(event -> {
            Actions.goTo(newWindow, "/fxml/studyWindow.fxml");
        });

        /**
         * Выход из приложения
         */
        exit.setOnAction(event -> exit());
    }
}
