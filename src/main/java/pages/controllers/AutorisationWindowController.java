package pages.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tools.Connect;
import tools.actions.Actions;
import tools.windows.OpenNewWindow;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AutorisationWindowController {

    OpenNewWindow newWindow = null;
    private static String username = "volkov_a";
    private static String password = "12345678";
    private static String URL = "jdbc:sqlserver://localhost\\ALEXANDER\\SQLEXPRESS:1433;database=Люди;database=Люди";
    private Connection connection;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField login_field;

    @FXML
    private Button authSignButton;


    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        assert password_field != null : "fx:id=\"password_field\" was not injected: check your FXML file 'autorisationWindow.fxml'.";
        assert login_field != null : "fx:id=\"login_field\" was not injected: check your FXML file 'autorisationWindow.fxml'.";
        assert authSignButton != null : "fx:id=\"authSignButton\" was not injected: check your FXML file 'autorisationWindow.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'autorisationWindow.fxml'.";

        backButton.setOnAction(event -> Actions.goTo(newWindow,"/fxml/open.fxml"));

        authSignButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String passText = password_field.getText().trim();

            if(!loginText.equals("") && !passText.equals("")){
                try {

                    connection = Connect.getConnection(URL, loginText, passText);
                    if(connection != null){
                        Actions.goTo(newWindow,"/fxml/teacherWindow.fxml");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    System.out.println("Connectiont: " + connection);
                }
            } else
                System.out.println("Error");
        });
    }

    }

