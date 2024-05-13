package com.foodscounting.foodscounting.controller;

import com.foodscounting.foodscounting.model.tech.Dish;
import com.foodscounting.foodscounting.model.tech.Layout;
import com.foodscounting.foodscounting.dao.LayoutDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.UUID;

public class LayoutDetailsController {

    @FXML
    private TableView<Dish> dishesTable;
    @FXML
    private TableColumn<Dish, String> columnDishName;
    @FXML
    private TableColumn<Dish, Integer> columnCaloricContent;

    private LayoutDAO layoutDao; // Экземпляр LayoutDAO

    public LayoutDetailsController() {
        this.layoutDao = new LayoutDAO(); // Инициализация LayoutDAO
    }

    public void initialize() {
        columnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCaloricContent.setCellValueFactory(new PropertyValueFactory<>("caloricContent"));
    }

    public void initData(Layout layout) {
        // Загрузка блюд, связанных с раскладкой
        dishesTable.setItems(getDishesForLayout(layout.getId()));
    }

    private ObservableList<Dish> getDishesForLayout(UUID layoutId) {
        return layoutDao.getDishesByLayoutId(layoutId);
    }
}
