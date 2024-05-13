package com.foodscounting.foodscounting.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import com.foodscounting.foodscounting.dao.LayoutDAO;

import java.sql.SQLException;

public class LayoutManagementController {
    @FXML private TableView<?> layoutTable;
    @FXML private TableColumn<?, Integer> columnNumber;
    @FXML private TableColumn<?, String> columnPeriod;
    @FXML private TableColumn<?, String> columnStatus;

    private MainViewController mainController;
    private LayoutDAO layoutDao = new LayoutDAO();

    // Сеттер для MainViewController
    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleAddLayout() {
        try {
            layoutDao.addWeeklyLayout();  // Метод, который нужно реализовать в DAO
            updateLayoutTable();          // Метод для обновления данных в таблице
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при добавлении раскладки: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    private void updateLayoutTable() {
        // Здесь будет код обновления данных в таблице
        // Этот метод должен извлекать все раскладки из базы данных и обновлять layoutTable
    }
}
