package com.foodscounting.foodscounting.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class LayoutManagementController {
    @FXML private TableView<?> layoutTable;
    @FXML private TableColumn<?, Integer> columnNumber;
    @FXML private TableColumn<?, String> columnPeriod;
    @FXML private TableColumn<?, String> columnStatus;

    private MainViewController mainController;

    // Сеттер для MainViewController
    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleAddLayout() {
        // Добавление новой раскладки
    }

    @FXML
    private void handleViewLayout() {
        // Просмотр выбранной раскладки
    }

    @FXML
    private void handleDeleteLayout() {
        // Удаление выбранной раскладки
    }

    @FXML
    private void handleApproveLayout() {
        // Утверждение выбранной раскладки
    }

    @FXML
    private void handleBackToMain() {
        if (mainController != null) {
            mainController.showMainView();
        }
    }
}
