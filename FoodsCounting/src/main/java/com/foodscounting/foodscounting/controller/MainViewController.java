package com.foodscounting.foodscounting.controller;

import com.foodscounting.foodscounting.controller.warehouse.WarehouseManagementController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.util.logging.Logger;

import java.io.IOException;

/**
 * Контроллер главного окна приложения, управляющий переключением между различными видами (вкладками) интерфейса пользователя.
 * Позволяет переключаться между управлением складом, управлением раскладками и другими функциональными частями приложения.
 * Обрабатывает действия пользователя и отображает соответствующие представления.
 */
public class MainViewController {
    private static final Logger LOGGER = Logger.getLogger(MainViewController.class.getName());

    @FXML
    private BorderPane mainContainer;
    private BorderPane mainView;

    public void switchToWarehouseManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodscounting/foodscounting/view/warehouse/WarehouseManagementView.fxml"));
            VBox warehouseView = loader.load();
            WarehouseManagementController controller = loader.getController();
            controller.setMainController(this);
            mainContainer.setCenter(warehouseView);
        } catch (IOException e) {
            LOGGER.severe("Error loading Warehouse Management view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void switchToLayoutManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodscounting/foodscounting/view/LayoutManagementView.fxml"));
            VBox layoutView = loader.load();
            LayoutManagementController controller = loader.getController();
            controller.setMainController(this);
            mainContainer.setCenter(layoutView);
        } catch (IOException e) {
            LOGGER.severe("Error loading Layout Management view: " + e.getMessage());
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
        switchToLayoutManagement();
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
