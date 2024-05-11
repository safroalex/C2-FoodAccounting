package com.foodscounting.foodscounting;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField inputField; // Поле для ввода текста

    private DatabaseConnector dbConnector = new DatabaseConnector();

    @FXML
    protected void onHelloButtonClick() {
        String userInput = inputField.getText();
        dbConnector.addRecord(userInput); // Добавление записи в базу данных
        welcomeText.setText("Data added!");
    }
}
