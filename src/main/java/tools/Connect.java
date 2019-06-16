package tools;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private static Connection connection = null;

    public static Connection getConnection(String URL, String username,String password) throws SQLException, ClassNotFoundException {

            Driver driver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, username, password);
            if(connection != null)
                System.out.print("Успешно!");
            else {
                System.out.print("Плохо!");
                System.exit(0);
            }
            return connection;
    }

    public static Connection getConnection() {
        return connection;
    }
}