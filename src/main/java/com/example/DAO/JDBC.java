package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC
{
    private static final String url = "jdbc:mysql://localhost:3306/carrentalprogram";
    private static final String user = "root";
    private static String password = "1234";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
