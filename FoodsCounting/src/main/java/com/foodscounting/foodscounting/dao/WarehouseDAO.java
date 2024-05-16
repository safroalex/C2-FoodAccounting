package com.foodscounting.foodscounting.dao;

import com.foodscounting.foodscounting.model.warehouse.*;
import com.foodscounting.foodscounting.utils.DatabaseConnector;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс WarehouseDAO управляет взаимодействием с базой данных для операций, связанных со складом.
 * Этот класс обеспечивает функциональность управления продуктовыми группами, продуктами, единицами измерения,
 * а также добавление и удаление записей о продуктах на складе.
 *
 * Основные методы:
 * - getProductGroups: извлекает список всех продуктовых групп.
 * - getProductNamesByGroup: возвращает список продуктов, принадлежащих к определенной группе.
 * - getUnitItems: извлекает список всех единиц измерения.
 * - findOrCreateWarehouseEntry: находит или создает запись склада для заданной даты.
 * - addProductToWarehouse: добавляет продукт на склад.
 * - getAllWarehouseEntries: извлекает информацию обо всех записях на складе.
 * - deleteWarehouseEntriesByDate: удаляет все записи склада по указанной дате.
 * - getProductsByDate: получает детализацию продуктов, хранящихся на складе по определенной дате.
 *
 * Эти методы используются для управления данными склада, обеспечивая централизованное хранение и доступ к информации о продуктах.
 */

public class WarehouseDAO {
    public List<GroupItem> getProductGroups() throws SQLException {
        List<GroupItem> groups = new ArrayList<>();
        String sql = "SELECT ID, Name FROM ProduktGroup ORDER BY Name";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                groups.add(new GroupItem(UUID.fromString(rs.getString("ID")), rs.getString("Name")));
            }
        }
        return groups;
    }


    public List<ProductItem> getProductNamesByGroup(UUID groupId) throws SQLException {
        List<ProductItem> items = new ArrayList<>();
        String sql = "SELECT ID, Name FROM Product WHERE ProduktGroupId = ? ORDER BY Name";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, groupId, java.sql.Types.OTHER);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("Name");
                items.add(new ProductItem(id, name));
            }
        }
        return items;
    }

    public List<UnitItem> getUnitItems() throws SQLException {
        List<UnitItem> units = new ArrayList<>();
        String sql = "SELECT ID, Name FROM Unit ORDER BY Name";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("Name");
                units.add(new UnitItem(id, name));
            }
        }
        return units;
    }

    public UUID findOrCreateWarehouseEntry(Date date) throws SQLException {
        UUID warehouseId = findWarehouseByDate(date);
        if (warehouseId == null) {
            warehouseId = createWarehouse(date);
        }
        return warehouseId;
    }

    private UUID findWarehouseByDate(Date date) throws SQLException {
        String sql = "SELECT ID FROM Warehouse WHERE DateDeposit = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, date);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return UUID.fromString(rs.getString("ID"));
            }
        }
        return null;
    }

    private UUID createWarehouse(Date date) throws SQLException {
        UUID newId = UUID.randomUUID();
        String sql = "INSERT INTO Warehouse (ID, DateDeposit) VALUES (?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, newId, java.sql.Types.OTHER);
            pstmt.setDate(2, date);
            pstmt.executeUpdate();
        }
        return newId;
    }

    public void addProductToWarehouse(UUID warehouseId, UUID productId, int number, UUID unitId, Date expiryDate, String remark) throws SQLException {
        String sql = "INSERT INTO QuantityProdukt (ID, WarehouseId, ProduktId, Number, UnitId, DateExpiry, Remark) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, UUID.randomUUID(), java.sql.Types.OTHER);
            pstmt.setObject(2, warehouseId, java.sql.Types.OTHER);
            pstmt.setObject(3, productId, java.sql.Types.OTHER);
            pstmt.setInt(4, number);
            pstmt.setObject(5, unitId, java.sql.Types.OTHER);
            pstmt.setDate(6, expiryDate);
            pstmt.setString(7, remark);
            pstmt.executeUpdate();
        }
    }


    public List<ProductRecord> getAllWarehouseEntries() throws SQLException {
        List<ProductRecord> entries = new ArrayList<>();
        String sql = "SELECT COUNT(ID) as Number, DateDeposit FROM Warehouse GROUP BY DateDeposit ORDER BY DateDeposit DESC";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int number = rs.getInt("Number");
                Date date = rs.getDate("DateDeposit");
                entries.add(new ProductRecord(number, date.toLocalDate()));
            }
        }
        return entries;
    }

    public void deleteWarehouseEntriesByDate(Date date) throws SQLException {
        String deleteProductsSql = "DELETE FROM QuantityProdukt WHERE WarehouseId IN (SELECT ID FROM Warehouse WHERE DateDeposit = ?)";
        String deleteWarehouseSql = "DELETE FROM Warehouse WHERE DateDeposit = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmtProducts = conn.prepareStatement(deleteProductsSql);
             PreparedStatement pstmtWarehouse = conn.prepareStatement(deleteWarehouseSql)) {

            // Удаление продуктов
            pstmtProducts.setDate(1, date);
            pstmtProducts.executeUpdate();

            // Удаление записей склада
            pstmtWarehouse.setDate(1, date);
            pstmtWarehouse.executeUpdate();
        }
    }

    public List<ProductDetail> getProductsByDate(LocalDate date) throws SQLException {
        List<ProductDetail> products = new ArrayList<>();
        String sql = "SELECT p.Name, qp.Number, u.Name as UnitName, pg.Name as GroupName, qp.DateExpiry, qp.Remark " +
                "FROM QuantityProdukt qp " +
                "JOIN Product p ON qp.ProduktId = p.ID " +
                "JOIN ProduktGroup pg ON p.ProduktGroupId = pg.ID " +
                "JOIN Unit u ON qp.UnitId = u.ID " +
                "WHERE qp.WarehouseId IN (SELECT ID FROM Warehouse WHERE DateDeposit = ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String group = rs.getString("GroupName");
                String name = rs.getString("Name");
                Integer quantity = rs.getInt("Number");
                String unit = rs.getString("UnitName");
                LocalDate expiryDate = rs.getDate("DateExpiry").toLocalDate();
                String remark = rs.getString("Remark");
                products.add(new ProductDetail(group, name, quantity, unit, expiryDate, remark));
            }
        }
        return products;
    }

}
