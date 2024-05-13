package com.foodscounting.foodscounting.controller;

import com.foodscounting.foodscounting.model.tech.Layout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import com.foodscounting.foodscounting.dao.LayoutDAO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class LayoutManagementController {
    @FXML private TableView<Layout> layoutTable; // Указываем, что таблица работает с объектами Layout
    @FXML private TableColumn<Layout, Integer> columnId;
    @FXML private TableColumn<Layout, Date> columnDateBegin;
    @FXML private TableColumn<Layout, Date> columnDateEnd;
    @FXML private TableColumn<Layout, String> columnStatus;

    private ObservableList<Layout> layouts = FXCollections.observableArrayList();


    private MainViewController mainController;
    private LayoutDAO layoutDao = new LayoutDAO();
    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDateBegin.setCellValueFactory(new PropertyValueFactory<>("dateBegin"));
        columnDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        updateLayoutTable();
    }
    // Сеттер для MainViewController
    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleAddLayout() {
        try {
            layoutDao.addWeeklyLayout();
            updateLayoutTable();
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
        Layout selectedLayout = layoutTable.getSelectionModel().getSelectedItem();
        if (selectedLayout != null) {
            openLayoutDetailsWindow(selectedLayout);
        } else {
            showAlert("Ошибка", "Не выбрана раскладка для просмотра", Alert.AlertType.WARNING);
        }
    }

    private void openLayoutDetailsWindow(Layout layout) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodscounting/foodscounting/view/LayoutDetails.fxml"));
            Parent root = loader.load();
            LayoutDetailsController controller = loader.getController();
            controller.initData(layout);

            Stage stage = new Stage();
            stage.setTitle("Детали раскладки: " + layout.getId().toString());
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось открыть окно деталей: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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
        try {
            layouts.clear();
            layouts.addAll(layoutDao.getLayouts());  // Загрузка всех раскладок
            layoutTable.setItems(layouts);
        } catch (SQLException e) {
            showAlert("Ошибка", "Не удалось загрузить раскладки: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
