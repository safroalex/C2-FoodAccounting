package com.foodscounting.foodscounting.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.time.LocalDate;

public class AddProductDialogController {
    @FXML private ComboBox<String> productGroupComboBox;
    @FXML private ComboBox<String> productNameComboBox;
    @FXML private TextField quantityField;
    @FXML private ComboBox<String> unitComboBox;
    @FXML private DatePicker expiryDatePicker;

    @FXML
    private void handleSaveProduct(ActionEvent event) {
        // Получение данных из полей
        String group = productGroupComboBox.getValue();
        String name = productNameComboBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText());
        String unit = unitComboBox.getValue();
        LocalDate expiryDate = expiryDatePicker.getValue();

        // Здесь должна быть логика сохранения данных в модель или базу данных
        System.out.println("Продукт добавлен: " + name);
        // Закрыть окно после сохранения
    }

    public void initialize() {
        // Инициализация ComboBox и других компонентов
        productGroupComboBox.getItems().addAll("Молочные продукты", "Хлебобулочные изделия", "Мясные продукты");
        unitComboBox.getItems().addAll("кг", "шт", "л");
    }
}
