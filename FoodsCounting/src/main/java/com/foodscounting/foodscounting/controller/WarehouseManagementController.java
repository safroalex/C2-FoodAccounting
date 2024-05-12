package com.foodscounting.foodscounting.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WarehouseManagementController {
    private MainViewController mainController;

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private void handleAddProduct(ActionEvent event) {
        // Логика добавления товара
    }

    @FXML
    private void handleRemoveProduct(ActionEvent event) {
        // Логика удаления товара
    }

    @FXML
    private void handleViewInventory(ActionEvent event) {
        // Логика просмотра остатков на складе
    }

    @FXML
    private void handleBackToMain(ActionEvent event) {
        mainController.showMainView();
    }
}
