package com.foodscounting.foodscounting.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.foodscounting.foodscounting.model.ProductDetail;

import java.time.LocalDate;
import java.util.List;

public class ProductDetailsController {
    @FXML
    private TableView<ProductDetail> detailsTable;
    @FXML
    private TableColumn<ProductDetail, String> groupColumn;
    @FXML
    private TableColumn<ProductDetail, String> nameColumn;
    @FXML
    private TableColumn<ProductDetail, Integer> quantityColumn;
    @FXML
    private TableColumn<ProductDetail, String> unitColumn;
    @FXML
    private TableColumn<ProductDetail, LocalDate> expiryDateColumn;
    @FXML
    private TableColumn<ProductDetail, String> remarkColumn;

    public void initialize() {
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
    }

    public void setProducts(List<ProductDetail> products) {
        detailsTable.getItems().setAll(products);
    }
}
