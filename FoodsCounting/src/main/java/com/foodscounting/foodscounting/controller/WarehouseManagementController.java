package com.foodscounting.foodscounting.controller;

import com.foodscounting.foodscounting.dao.WarehouseDAO;
import com.foodscounting.foodscounting.model.ProductDetail;
import com.foodscounting.foodscounting.model.ProductRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class WarehouseManagementController {
    @FXML
    private TableView<ProductRecord> inventoryTable;
    @FXML
    private TableColumn<ProductRecord, Integer> columnNumber;
    @FXML
    private TableColumn<ProductRecord, LocalDate> columnDate;

    private WarehouseDAO warehouseDao = new WarehouseDAO();

    private ObservableList<ProductRecord> products = FXCollections.observableArrayList();
    private MainViewController mainController;
    public void initialize() {
        columnNumber.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        columnDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        inventoryTable.setItems(products);
        updateInventoryTable();
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodscounting/foodscounting/view/AddProductDialog.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Добавление продукта");
            stage.setScene(scene);
            stage.showAndWait();
            updateInventoryTable();
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateInventoryTable() {
        try {
            List<ProductRecord> entries = warehouseDao.getAllWarehouseEntries();
            products.setAll(entries);
        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке записей склада: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveProduct(ActionEvent event) {
        ProductRecord selectedRecord = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            try {
                // Получаем LocalDate из ObjectProperty
                LocalDate selectedDate = selectedRecord.dateProperty().get();
                // Конвертируем LocalDate в java.sql.Date для использования в SQL запросе
                warehouseDao.deleteWarehouseEntriesByDate(Date.valueOf(selectedDate));
                // Обновляем таблицу
                updateInventoryTable();
                System.out.println("Запись успешно удалена.");
            } catch (SQLException e) {
                System.out.println("Ошибка при удалении записей: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Не выбрана ни одна запись для удаления.");
        }
    }




    @FXML
    private void handleViewInventory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodscounting/foodscounting/view/ProductDetailsView.fxml"));
            Parent root = loader.load();
            ProductDetailsController controller = loader.getController();

            ProductRecord selectedRecord = inventoryTable.getSelectionModel().getSelectedItem();
            if (selectedRecord != null) {
                LocalDate selectedDate = selectedRecord.getDate();  // Получение LocalDate

                List<ProductDetail> products = warehouseDao.getProductsByDate(selectedDate); // Передаём LocalDate
                controller.setProducts(products);

                Stage stage = new Stage();
                stage.setTitle("Детали продукта на " + selectedDate.toString());
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось загрузить данные о продуктах: " + e.getMessage());
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void handleBackToMain(ActionEvent event) {
        mainController.showMainView();
    }
}


