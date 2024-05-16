package com.foodscounting.foodscounting.dao;

import com.foodscounting.foodscounting.model.tech.Dish;
import com.foodscounting.foodscounting.model.tech.DishDetails;
import com.foodscounting.foodscounting.model.tech.Layout;
import com.foodscounting.foodscounting.utils.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class LayoutDAO {

    public void addWeeklyLayout() throws SQLException {
        Connection connection = DatabaseConnector.connect();
        try {
            connection.setAutoCommit(false); // Отключение автокоммита для контроля транзакций

            UUID layoutId = UUID.randomUUID();
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(6);
            String insertLayoutSQL = "INSERT INTO Layout (ID, DateBegin, DateEnd, Status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertLayoutSQL)) {
                ps.setObject(1, layoutId, java.sql.Types.OTHER);  // Явно указываем тип UUID
                ps.setDate(2, Date.valueOf(startDate));
                ps.setDate(3, Date.valueOf(endDate));
                ps.setString(4, "New");
                ps.executeUpdate();
            }

            distributeDishes(connection, layoutId);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private void distributeDishes(Connection connection, UUID layoutId) throws SQLException {
        String selectDishesSQL = "SELECT ID, Name, CaloricContent FROM Dish ORDER BY CaloricContent DESC";
        List<DishDetails> dishes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(selectDishesSQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("Name");
                int caloricContent = rs.getInt("CaloricContent");
                dishes.add(new DishDetails(id, name, caloricContent));
            }
        }

        //жадный алгоритм
        for (int day = 0; day < 7; day++) {
            int dailyCalories = 0;
            for (DishDetails dish : dishes) {
                if (dailyCalories + dish.getCaloricContent() <= 2000 && canPrepareDish(connection, dish.getId())) {
                    dailyCalories += dish.getCaloricContent();
                    String insertLayoutDishSQL = "INSERT INTO LayoutDishes (ID, LayoutId, DishId, Quantity) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(insertLayoutDishSQL)) {
                        ps.setObject(1, UUID.randomUUID(), java.sql.Types.OTHER);
                        ps.setObject(2, layoutId, java.sql.Types.OTHER);
                        ps.setObject(3, dish.getId(), java.sql.Types.OTHER);
                        ps.setInt(4, 1);
                        ps.executeUpdate();
                    }
                    if (dailyCalories >= 1800) break; //достигнута минимальная калорийность
                }
            }
        }
    }



    private boolean canPrepareDish(Connection connection, UUID dishId) throws SQLException {
        String checkIngredientsSQL = "SELECT di.ProduktId, di.Quantity FROM DishIngredients di WHERE di.DishId = ?";
        Map<UUID, Double> requiredIngredients = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(checkIngredientsSQL)) {
            ps.setObject(1, dishId, java.sql.Types.OTHER);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                requiredIngredients.put(UUID.fromString(rs.getString("ProduktId")), rs.getDouble("Quantity"));
            }
        }

        for (Map.Entry<UUID, Double> entry : requiredIngredients.entrySet()) {
            String checkStockSQL = "SELECT SUM(Number) AS Total FROM QuantityProdukt WHERE ProduktId = ?";
            try (PreparedStatement ps = connection.prepareStatement(checkStockSQL)) {
                ps.setObject(1, entry.getKey(), java.sql.Types.OTHER);
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getDouble("Total") < entry.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }






    public List<Layout> getLayouts() throws SQLException {
        List<Layout> layouts = new ArrayList<>();
        String query = "SELECT ID, DateBegin, DateEnd, Status FROM Layout";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                Date dateBegin = rs.getDate("DateBegin");
                Date dateEnd = rs.getDate("DateEnd");
                String status = rs.getString("Status");
                layouts.add(new Layout(id, dateBegin, dateEnd, status));
            }
        }
        return layouts;
    }

    public ObservableList<Dish> getDishesByLayoutId(UUID layoutId) {
        ObservableList<Dish> dishes = FXCollections.observableArrayList();
        String query = "SELECT Dish.Name, Dish.CaloricContent FROM Dish JOIN LayoutDishes ON Dish.ID = LayoutDishes.DishId WHERE LayoutDishes.LayoutId = ?";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, layoutId, java.sql.Types.OTHER);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                int caloricContent = rs.getInt("CaloricContent");
                dishes.add(new Dish(name, caloricContent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
}
