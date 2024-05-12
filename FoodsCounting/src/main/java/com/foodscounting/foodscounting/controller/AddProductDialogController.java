package com.foodscounting.foodscounting.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.foodscounting.foodscounting.model.GroupItem;
import com.foodscounting.foodscounting.model.ProductItem;
import com.foodscounting.foodscounting.model.UnitItem;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import com.foodscounting.foodscounting.dao.WarehouseDAO;


public class AddProductDialogController {
    @FXML private ComboBox<GroupItem> productGroupComboBox;
    @FXML private ComboBox<ProductItem> productNameComboBox;
    @FXML private ComboBox<UnitItem> unitComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private DatePicker expiryDatePicker;
    @FXML
    private TextField remarkField;

    private WarehouseDAO warehouseDao = new WarehouseDAO();

    @FXML
    private void handleSaveProduct(ActionEvent event) {
        try {
            ProductItem selectedProduct = productNameComboBox.getValue();
            UnitItem selectedUnit = unitComboBox.getValue();

            UUID productId = selectedProduct.getId();
            UUID unitId = selectedUnit.getId();

            int number = Integer.parseInt(quantityField.getText().trim());
            LocalDate localDate = expiryDatePicker.getValue();
            Date expiryDate = Date.valueOf(localDate);
            String remark = remarkField.getText();

            UUID warehouseId = warehouseDao.findOrCreateWarehouseEntry(new Date(System.currentTimeMillis()));
            warehouseDao.addProductToWarehouse(warehouseId, productId, number, unitId, expiryDate, remark);

            showAlert("Добавление продукта", "Продукт успешно добавлен.");
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при добавлении продукта: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showAlert("Ошибка ввода", "Проверьте введенные данные.");
        }
    }

    public void initialize() {
        try {
            List<GroupItem> groups = warehouseDao.getProductGroups();
            productGroupComboBox.getItems().setAll(groups);

            productGroupComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    updateProductNames(newVal.getId());
                }
            });

            List<UnitItem> units = warehouseDao.getUnitItems();
            unitComboBox.getItems().setAll(units);
        } catch (SQLException e) {
            showAlert("Ошибка загрузки данных", "Не удалось загрузить данные из базы: " + e.getMessage());
        }
    }

    private void updateProductNames(UUID groupId) {
        try {
            List<ProductItem> products = warehouseDao.getProductNamesByGroup(groupId);
            productNameComboBox.getItems().setAll(products);
        } catch (SQLException e) {
            showAlert("Ошибка загрузки данных", "Ошибка при фильтрации продуктов: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}