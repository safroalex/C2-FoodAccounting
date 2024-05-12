package com.foodscounting.foodscounting.dao;

import com.foodscounting.foodscounting.model.Record;
import com.foodscounting.foodscounting.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecordDao {
    private DatabaseConnector dbConnector;

    public RecordDao(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void addRecord(Record record) {
        String sql = "INSERT INTO messages (content) VALUES (?)";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.getContent());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
