package com.foodscounting.foodscounting.controller;

import com.foodscounting.foodscounting.model.ProductRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class WarehouseManagementController {
    @FXML
    private TableView<ProductRecord> inventoryTable;
    @FXML
    private TableColumn<ProductRecord, Integer> columnNumber;
    @FXML
    private TableColumn<ProductRecord, LocalDate> columnDate;

    private ObservableList<ProductRecord> products = FXCollections.observableArrayList();
    private MainViewController mainController;
    public void initialize() {
        columnNumber.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        columnDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        inventoryTable.setItems(products);
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodscounting/foodscounting/view/AddProductDialog.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300); // Увеличенные размеры окна
            Stage stage = new Stage();
            stage.setTitle("Добавление продукта");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }




    @FXML
    private void handleRemoveProduct(ActionEvent event) {
        int selectedIndex = inventoryTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            inventoryTable.getItems().remove(selectedIndex);
        }
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


