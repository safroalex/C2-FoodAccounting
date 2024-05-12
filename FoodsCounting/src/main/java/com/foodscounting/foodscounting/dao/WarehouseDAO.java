package com.foodscounting.foodscounting.dao;

import com.foodscounting.foodscounting.model.GroupItem;
import com.foodscounting.foodscounting.model.ProductItem;
import com.foodscounting.foodscounting.model.ProductRecord;
import com.foodscounting.foodscounting.model.UnitItem;
import com.foodscounting.foodscounting.utils.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

}
