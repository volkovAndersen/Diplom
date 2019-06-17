package pages.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import persons.Child;
import persons.Result;
import persons.current.CurrentChild;
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
    private ObservableList<Result> dataResult;
    private ObservableList<Child> dataChild;


    @FXML
    private TableColumn<Result, Integer> resultColumn;

    @FXML
    private TableColumn<Result, String> nameColumn;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Result, String> dateColumn;

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
    private TableColumn<Child, String> nameChild;

    @FXML
    private TableColumn<Child, Integer> idChild;

    @FXML
    private TableColumn<Child, String> oldChild;

    @FXML
    private TableView<Child> childTable;

    @FXML
    private Button deleteButton;

    @FXML
    private Button setCurrent;

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
    void addChild(ActionEvent event) throws SQLException {
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
                            "           ('" + nameField.getText() +"' \n" +
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
                reloadFunc();
            }
        }
        else {
            System.out.println("Error");
        }

    }

    private void initCollumns() throws SQLException {
        idColumn.setCellValueFactory(new PropertyValueFactory<Result, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("nameChild"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("date"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<Result, Integer>("result"));

        idChild.setCellValueFactory(new PropertyValueFactory<Child, Integer>("id"));
        nameChild.setCellValueFactory(new PropertyValueFactory<Child, String>("name"));
        oldChild.setCellValueFactory(new PropertyValueFactory<Child, String>("old"));

        reloadFunc();
    }

    private void reloadFunc() throws SQLException {
        getInitalTableDataResult();
        getInitalTableDataChild();
        resultTable.setItems(dataResult);
        childTable.setItems(dataChild);
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

    private void getInitalTableDataChild() throws SQLException {
        List<Child> list = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT  [IDРебенка]\n" +
                "      ,[Имя]\n" +
                "      ,[Возраст]\n" +
                "  FROM [Люди].[dbo].[Ребенок]");

        while (resultSet.next()){
            Child child = new Child();

            child.setId(resultSet.getInt(1));
            child.setName(resultSet.getString(2));
            child.setOld(resultSet.getString(3));

            list.add(child);
        }

        resultSet.close();
        statement.close();

        dataChild = FXCollections.observableList(list);
    }

    private void getInitalTableDataResult() throws SQLException {
        List<Result> list = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT " +
                "[IDРезультата]\n" +
                "      ,[Результат]\n" +
                "      ,[Дата]\n" +
                "      ,[Люди].[dbo].Ребенок.Имя\n" +
                "  FROM [Люди].[dbo].[Результаты], [Люди].[dbo].Ребенок\n" +
                "  WHERE [Люди].[dbo].Ребенок.IDРебенка = [Люди].[dbo].Результаты.FKРебенка");


        while (resultSet.next()){
            Result result = new Result();

            result.setId(resultSet.getInt(1));
            result.setNameChild(resultSet.getString(4));
            result.setResult(resultSet.getInt(2));
            result.setDate(resultSet.getString(3));

            result.printInfo();
            list.add(result);
        }

        resultSet.close();
        statement.close();

        dataResult = FXCollections.observableList(list);
    }



    @FXML
    void delete(ActionEvent event) throws SQLException {

        try{
            Child child = childTable.getSelectionModel().getSelectedItem();
            connection = Connect.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("DELETE FROM [dbo].[Ребенок]\n" +
                    "      WHERE IDРебенка = " + child.getId());
        }
        finally {
            reloadFunc();
        }
    }

    @FXML
    void current(ActionEvent event) {
        Child child = childTable.getSelectionModel().getSelectedItem();
        CurrentChild.setIdOfCurrentChild(child.getId());
    }
}
