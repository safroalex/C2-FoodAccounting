package com.foodscounting.foodscounting.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://188.134.87.227:5432/foodscounting";
    private static final String USER = "postgres";
    private static final String PASSWORD = "notsecret";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
