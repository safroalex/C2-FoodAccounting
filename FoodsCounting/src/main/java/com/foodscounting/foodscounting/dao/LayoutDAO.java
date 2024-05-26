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

/**
 * Класс LayoutDAO управляет взаимодействием с базой данных для операций, связанных с раскладками.
 * Этот класс обеспечивает функциональность добавления, удаления, утверждения раскладок,
 * а также извлечения информации о раскладках и связанных с ними блюдах.
 *
 * Основные методы:
 * - addWeeklyLayout: создает новую раскладку на неделю, распределяет блюда исходя из калорийности.
 * - distributeDishes: распределяет блюда по дням недели в рамках созданной раскладки.
 * - canPrepareDish: проверяет, достаточно ли ингредиентов на складе для приготовления блюда.
 * - getLayouts: извлекает список всех раскладок из базы данных.
 * - deleteLayout: удаляет указанную раскладку и связанные с ней данные из базы данных.
 * - approveLayout: утверждает раскладку и списывает необходимые продукты со склада.
 * - getDishesByLayoutId: возвращает список блюд для конкретной раскладки.
 *
 * Каждый метод управляет своими SQL-запросами и обеспечивает соответствующие транзакционные гарантии.
 */
public class LayoutDAO {

    public void addWeeklyLayout() throws SQLException {
        Connection connection = DatabaseConnector.connect();
        try {
            connection.setAutoCommit(false);

            UUID layoutId = UUID.randomUUID();
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(6);
            String insertLayoutSQL = "INSERT INTO Layout (ID, DateBegin, DateEnd, Status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertLayoutSQL)) {
                ps.setObject(1, layoutId, java.sql.Types.OTHER);
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
        // sql-запрос для получения списка блюд, отсортированных по убыванию калорийности
        String selectDishesSQL = "SELECT ID, Name, CaloricContent FROM Dish ORDER BY CaloricContent DESC";
        List<DishDetails> dishes = new ArrayList<>();
        Map<UUID, Integer> dishCounts = new HashMap<>(); // словарь для отслеживания количества использований каждого блюда за неделю

        // выполнение sql-запроса и заполнение списка блюд
        try (PreparedStatement ps = connection.prepareStatement(selectDishesSQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("Name");
                int caloricContent = rs.getInt("CaloricContent");
                dishes.add(new DishDetails(id, name, caloricContent));
                dishCounts.put(id, 0); // инициализация счетчика для каждого блюда
            }
        }

        // перетасовка списка блюд для случайного распределения
        Collections.shuffle(dishes);
        for (int day = 1; day <= 7; day++) {
            int dailyCalories = 0;
            Set<UUID> usedDishes = new HashSet<>(); // множество для отслеживания использованных блюд в этот день

            // повторная перетасовка списка блюд перед началом каждого нового дня
            Collections.shuffle(dishes);
            for (DishDetails dish : dishes) {
                // проверка возможности приготовления блюда и его отсутствия в использованных ранее в этот день
                boolean canBePrepared = canPrepareDish(connection, dish.getId()) && !usedDishes.contains(dish.getId());
                // проверка, что блюдо использовалось менее трех раз за неделю
                boolean underWeeklyLimit = dishCounts.get(dish.getId()) < 3;

                // условие добавления блюда в раскладку: не превышает 2000 ккал или помогает достичь минимума в 1800 ккал
                if (canBePrepared && underWeeklyLimit && (dailyCalories + dish.getCaloricContent() <= 2000 || (dailyCalories < 1800 && dailyCalories + dish.getCaloricContent() > 1800))) {
                    dailyCalories += dish.getCaloricContent();
                    usedDishes.add(dish.getId());
                    dishCounts.put(dish.getId(), dishCounts.get(dish.getId()) + 1); // увеличиваем счетчик использования блюда

                    // sql-запрос для вставки информации о блюде в раскладку дня
                    String insertLayoutDishSQL = "INSERT INTO LayoutDishes (ID, LayoutId, DishId, Quantity, DayOfWeek) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(insertLayoutDishSQL)) {
                        ps.setObject(1, UUID.randomUUID(), java.sql.Types.OTHER);
                        ps.setObject(2, layoutId, java.sql.Types.OTHER);
                        ps.setObject(3, dish.getId(), java.sql.Types.OTHER);
                        ps.setInt(4, 1);
                        ps.setString(5, String.valueOf(day));
                        ps.executeUpdate();
                    }
                }
                // если достигнут минимум калорийности, завершаем распределение на этот день
                if (dailyCalories >= 1800) break;
            }

            // вывод предупреждения, если не удалось набрать минимально необходимое количество калорий
            if (dailyCalories < 1800) {
                System.out.println("Внимание: Не удалось набрать достаточное количество калорий для дня " + day);
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
        String query = "SELECT Dish.Name, Dish.CaloricContent, LayoutDishes.DayOfWeek FROM Dish JOIN LayoutDishes ON Dish.ID = LayoutDishes.DishId WHERE LayoutDishes.LayoutId = ?";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, layoutId, java.sql.Types.OTHER);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                int caloricContent = rs.getInt("CaloricContent");
                String dayOfWeek = rs.getString("DayOfWeek");
                Dish dish = new Dish(name, caloricContent);
                dish.setDayOfWeek(dayOfWeek);
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public void deleteLayout(UUID layoutId) throws SQLException {
        Connection connection = DatabaseConnector.connect();
        try {
            connection.setAutoCommit(false);

            String deleteLayoutDishesSQL = "DELETE FROM LayoutDishes WHERE LayoutId = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteLayoutDishesSQL)) {
                ps.setObject(1, layoutId, java.sql.Types.OTHER);
                ps.executeUpdate();
            }

            String deleteLayoutSQL = "DELETE FROM Layout WHERE ID = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteLayoutSQL)) {
                ps.setObject(1, layoutId, java.sql.Types.OTHER);
                ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public void approveLayout(UUID layoutId) throws SQLException {
        Connection connection = DatabaseConnector.connect();
        try {
            connection.setAutoCommit(false);

            // обновление статуса раскладки
            String updateStatusSQL = "UPDATE Layout SET Status = 'Approved' WHERE ID = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateStatusSQL)) {
                ps.setObject(1, layoutId, java.sql.Types.OTHER);
                ps.executeUpdate();
            }

            // списание продуктов со склада
            String getIngredientsSQL = "SELECT DishId, Quantity FROM LayoutDishes WHERE LayoutId = ?";
            try (PreparedStatement ps = connection.prepareStatement(getIngredientsSQL)) {
                ps.setObject(1, layoutId, java.sql.Types.OTHER);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    UUID dishId = UUID.fromString(rs.getString("DishId"));
                    int quantity = rs.getInt("Quantity");
                    decrementStock(connection, dishId, quantity);
                }
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private void decrementStock(Connection connection, UUID dishId, int quantity) throws SQLException {
        String ingredientsSQL = "SELECT ProduktId, Quantity FROM DishIngredients WHERE DishId = ?";
        try (PreparedStatement ps = connection.prepareStatement(ingredientsSQL)) {
            ps.setObject(1, dishId, java.sql.Types.OTHER);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID produktId = UUID.fromString(rs.getString("ProduktId"));
                double requiredQuantity = rs.getDouble("Quantity") * quantity;
                String updateStockSQL = "UPDATE QuantityProdukt SET Number = Number - ? WHERE ProduktId = ?";
                try (PreparedStatement updatePs = connection.prepareStatement(updateStockSQL)) {
                    updatePs.setDouble(1, requiredQuantity);
                    updatePs.setObject(2, produktId, java.sql.Types.OTHER);
                    updatePs.executeUpdate();
                }
            }
        }
    }
}
