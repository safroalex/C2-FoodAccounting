package com.foodscounting.foodscounting.dao;

import com.foodscounting.foodscounting.utils.DatabaseConnector;

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
        String selectDishesSQL = "SELECT ID, CaloricContent FROM Dish";
        List<Dish> dishes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(selectDishesSQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dishes.add(new Dish(UUID.fromString(rs.getString("ID")), rs.getInt("CaloricContent")));
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
                    String insertLayoutDishSQL = "INSERT INTO LayoutDishes (ID, LayoutId, DishId, Quantity) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(insertLayoutDishSQL)) {
                        ps.setObject(1, UUID.randomUUID());
                        ps.setObject(2, layoutId);
                        ps.setObject(3, dish.getId());
                        ps.setInt(4, 1); // Предполагаем, что одно блюдо добавляется один раз
                        ps.executeUpdate();
                    }
                }
            }
        }
    }

    private boolean canPrepareDish(Connection connection, Dish dish) throws SQLException {
        // Проверка наличия всех необходимых ингредиентов на складе
        String checkIngredientsSQL = "SELECT di.ProduktId, di.Quantity FROM DishIngredients di WHERE di.DishId = ?";
        Map<UUID, Double> requiredIngredients = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(checkIngredientsSQL)) {
            ps.setObject(1, dish.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                requiredIngredients.put(UUID.fromString(rs.getString("ProduktId")), rs.getDouble("Quantity"));
            }
        }

        for (Map.Entry<UUID, Double> entry : requiredIngredients.entrySet()) {
            String checkStockSQL = "SELECT SUM(Number) AS Total FROM QuantityProdukt WHERE ProduktId = ?";
            try (PreparedStatement ps = connection.prepareStatement(checkStockSQL)) {
                ps.setObject(1, entry.getKey());
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getDouble("Total") < entry.getValue()) {
                    return false; // Недостаточно продуктов на складе
                }
            }
        }
        return true; // Все продукты в достаточном количестве
    }

    static class Dish {
        private UUID id;
        private int caloricContent;

        public Dish(UUID id, int caloricContent) {
            this.id = id;
            this.caloricContent = caloricContent;
        }

        public UUID getId() {
            return id;
        }

        public int getCaloricContent() {
            return caloricContent;
        }
    }
}
