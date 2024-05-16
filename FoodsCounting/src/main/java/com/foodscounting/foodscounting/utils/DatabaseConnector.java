package com.foodscounting.foodscounting.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Утилитный класс для установки соединения с базой данных PostgreSQL.
 * Предоставляет метод для соединения с базой данных на основе заранее определенных параметров URL, пользователя и пароля.
 */
public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://188.134.87.227:5432/foodscounting";
    private static final String USER = "postgres";
    private static final String PASSWORD = "notsecret";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
