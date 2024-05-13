package com.foodscounting.foodscounting.dao;

import com.foodscounting.foodscounting.model.tech.Dish;
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

            // Создание раскладки на неделю
            UUID layoutId = UUID.randomUUID();
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(6);
            String insertLayoutSQL = "INSERT INTO Layout (ID, DateBegin, DateEnd, Status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertLayoutSQL)) {
                ps.setObject(1, layoutId);
                ps.setDate(2, Date.valueOf(startDate));
                ps.setDate(3, Date.valueOf(endDate));
                ps.setString(4, "New");
                ps.executeUpdate();
            }

            // Распределение блюд по дням с учетом калорийности
            distributeDishes(connection, layoutId);

            connection.commit(); // Фиксация транзакции
        } catch (SQLException e) {
            connection.rollback(); // Откат в случае ошибки
            throw e;
        } finally {
            connection.setAutoCommit(true); // Включение автокоммита обратно
            connection.close();
        }
    }

    private void distributeDishes(Connection connection, UUID layoutId) throws SQLException {
        String selectDishesSQL = "SELECT Name, CaloricContent FROM Dish";
        List<Dish> dishes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(selectDishesSQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                int caloricContent = rs.getInt("CaloricContent");
                dishes.add(new Dish(name, caloricContent));  // Создание объектов Dish
            }
        }

        Random rand = new Random();
        int totalCalories = 0;
        for (int i = 0; i < 7; i++) { // Для каждого дня в неделе
            totalCalories = 0;
            List<Dish> dailyDishes = new ArrayList<>();
            while (totalCalories < 1800) { // Подбор блюд до достижения минимальной калорийности
                Dish dish = dishes.get(rand.nextInt(dishes.size()));
                if (totalCalories + dish.getCaloricContent() <= 2000 && canPrepareDish(connection, dish)) {
                    dailyDishes.add(dish);
                    totalCalories += dish.getCaloricContent();
                    String insertLayoutDishSQL = "INSERT INTO LayoutDishes (LayoutId, DishId, Quantity) VALUES (?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(insertLayoutDishSQL)) {
                        ps.setObject(1, layoutId);
                        ps.setString(2, dish.getName());  // Предположим, что вы сохраняете имя как идентификатор
                        ps.setInt(3, 1); // Предполагаем, что одно блюдо добавляется один раз
                        ps.executeUpdate();
                    }
                }
            }
        }
    }


    private boolean canPrepareDish(Connection connection, Dish dish) throws SQLException {
        // Проверка наличия всех необходимых ингредиентов на складе
        String checkIngredientsSQL = "SELECT di.ProduktId, di.Quantity FROM DishIngredients di JOIN Dish ON di.DishId = Dish.ID WHERE Dish.Name = ?";
        Map<String, Double> requiredIngredients = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(checkIngredientsSQL)) {
            ps.setString(1, dish.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                requiredIngredients.put(rs.getString("ProduktId"), rs.getDouble("Quantity"));
            }
        }

        for (Map.Entry<String, Double> entry : requiredIngredients.entrySet()) {
            String checkStockSQL = "SELECT SUM(Number) AS Total FROM QuantityProdukt WHERE ProduktId = ?";
            try (PreparedStatement ps = connection.prepareStatement(checkStockSQL)) {
                ps.setString(1, entry.getKey());
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getDouble("Total") < entry.getValue()) {
                    return false; // Недостаточно продуктов на складе
                }
            }
        }
        return true; // Все продукты в достаточном количестве
    }


    // Добавление метода getLayouts для загрузки всех раскладок
    public List<Layout> getLayouts() throws SQLException {
        List<Layout> layouts = new ArrayList<>();
        String query = "SELECT ID, DateBegin, DateEnd, Status FROM Layout";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID")); // Получаем ID как UUID
                Date dateBegin = rs.getDate("DateBegin");
                Date dateEnd = rs.getDate("DateEnd");
                String status = rs.getString("Status");
                // Создаем объект Layout, передавая UUID в качестве идентификатора
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
            ps.setObject(1, layoutId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                int caloricContent = rs.getInt("CaloricContent");
                dishes.add(new Dish(name, caloricContent)); // Создание объектов Dish с использованием имени
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

}
