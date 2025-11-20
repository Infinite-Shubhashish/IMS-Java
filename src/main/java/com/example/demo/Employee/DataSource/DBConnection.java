package com.example.demo.Employee.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException{
        String url = "jdbc:h2:~/test";
        String username = "sa";
        String password = "";
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }
}
