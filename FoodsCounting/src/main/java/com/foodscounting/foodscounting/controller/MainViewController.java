package com.foodscounting.foodscounting.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.logging.Logger;

import java.io.IOException;

public class MainViewController {
    private static final Logger LOGGER = Logger.getLogger(MainViewController.class.getName());

    @FXML
    private BorderPane mainContainer;
    private BorderPane mainView;

    public void switchToWarehouseManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodscounting/foodscounting/view/WarehouseManagementView.fxml"));
            VBox warehouseView = loader.load();
            WarehouseManagementController controller = loader.getController();
            controller.setMainController(this);
            mainContainer.setCenter(warehouseView);
        } catch (IOException e) {
            LOGGER.severe("Error loading Warehouse Management view: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void showMainView() {
        if (mainContainer.getCenter() != mainView) {
            mainContainer.setCenter(mainView);
        }
    }




    @FXML
    private void handleWarehouse(ActionEvent event) {
        try {
            switchToWarehouseManagement();
        } catch (Exception e) {
            LOGGER.severe("Error switching to Warehouse Management: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleLayouts(ActionEvent event) {
        // Implementation
    }

    @FXML
    private void handleTechCards(ActionEvent event) {
        // Implementation
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        showAlert("About", "This is the About section of the application.");
    }

    @FXML
    private void handleDocumentation(ActionEvent event) {
        showAlert("Documentation", "Documentation can be found at [URL].");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
