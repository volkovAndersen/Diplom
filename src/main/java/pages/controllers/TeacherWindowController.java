package pages.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import persons.Child;
import persons.Result;
import tools.Connect;
import tools.actions.Actions;
import tools.windows.OpenNewWindow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherWindowController {

    OpenNewWindow newWindow = null;
    private Connection connection;
    private ObservableList<Result> data;


    @FXML
    private TableColumn<Result, String> resultColumn;

    @FXML
    private TableColumn<Result, String> nameColumn;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Result, Integer> dateColumn;

    @FXML
    private TableColumn<Result, Integer> idColumn;

    @FXML
    private TableView<Result> resultTable;

    @FXML
    private Button addChildButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField oldField;

    @FXML
    private ComboBox<String> childSet;

    @FXML
    void initialize() throws SQLException {

        assert oldField != null : "fx:id=\"oldField\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert resultColumn != null : "fx:id=\"resultColumn\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert resultTable != null : "fx:id=\"resultTable\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert dateColumn != null : "fx:id=\"dateColumn\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert addChildButton != null : "fx:id=\"addChildButton\" was not injected: check your FXML file 'teacherWindow.fxml'.";
        assert idColumn != null : "fx:id=\"idColumn\" was not injected: check your FXML file 'teacherWindow.fxml'.";


        connection = Connect.getConnection( );
        initCollumns();

        backButton.setOnAction(event -> Actions.goTo(newWindow, "/fxml/open.fxml"));
    }

    @FXML
    void addChild(ActionEvent event){
        if (!nameField.equals("") && !oldField.equals(""))  {
            try {
                connection = Connect.getConnection();
                if (connection != null) {
                    Statement statement = connection.createStatement( );
                    ResultSet resultSet = statement.executeQuery(
                            "INSERT INTO [dbo].[Ребенок]\n" +
                            "           ([Имя]\n" +
                            "           ,[Возраст])\n" +
                            "     VALUES\n" +
                            "           (" + nameField.getText() +" \n" +
                            "           ," + oldField.getText()+ ")\n");

                    resultSet.close();
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace( );
            }
            finally {
                nameField.setText("");
                oldField.setText("");

            }
        }
        else {
            System.out.println("Error");
        }

    }

    private void initCollumns() throws SQLException {
        idColumn.setCellValueFactory(new PropertyValueFactory<Result, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Result, Integer>("date"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("result"));
        reloadFunc();
    }

    private void reloadFunc() throws SQLException {
        getInitalTableData();
        resultTable.setItems(data);
    }

//    private void initComboBox() throws SQLException {
//        List<Child> list = new ArrayList<>();
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT  [IDРебенка]\n" +
//                "      ,[Имя]\n" +
//                "      ,[Возраст]\n" +
//                "  FROM [Люди].[dbo].[Ребенок]");
//
//        while ((resultSet.next())){
//            Child child = new Child();
//
//            child.setId(resultSet.getInt(1));
//            child.setName(resultSet.getString(2));
//            child.setOld(resultSet.getString(4));
//
//            list.add(child);
//        }
//
//        resultSet.close();
//        statement.close();
//
//    }

    private void getInitalTableData() throws SQLException {
        List<Result> list = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT   [IDРезультата]\n" +
                "      ,[Люди].[dbo].[Ребенок].Имя\n" +
                "      ,[Результат]\n" +
                "      ,[Дата]\n" +
                "  FROM [Люди].[dbo].[Результаты], [Люди].[dbo].[Ребенок]\n" +
                "  WHERE Результаты.FKРебенка = Ребенок.IDРебенка\n");


        while (resultSet.next()){
            Result result = new Result();

            result.setId(resultSet.getInt(1));
            result.setResult(resultSet.getInt(3));
            result.setDate(resultSet.getString(4));
            result.setNameChild(resultSet.getString(2));

            result.printInfo();
            list.add(result);
        }

        resultSet.close();
        statement.close();

        data = FXCollections.observableList(list);
    }
}
