package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.finance.config.DatabaseConfig;
import com.finance.entities.Reminder;

@Repository
public class ReminderDAO {
	public int saveReminder(Reminder reminder) {
        String sql = "INSERT INTO reminder (bill_name, amount, due_date, is_paid) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, reminder.getBillName());
            stmt.setDouble(2, reminder.getAmount());
            stmt.setObject(3, reminder.getDueDate());
            stmt.setBoolean(4, reminder.isPaid());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving reminder: " + e.getMessage(), e);
        }
        throw new RuntimeException("Failed to retrieve generated ID for reminder");
    }
	
	public Reminder getReminderById(int id) {
        String sql = "SELECT * FROM reminder WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setId(rs.getInt("id"));
                reminder.setBillName(rs.getString("bill_name"));
                reminder.setAmount(rs.getDouble("amount"));
                reminder.setDueDate(rs.getObject("due_date", LocalDate.class));
                reminder.setPaid(rs.getBoolean("is_paid"));
                return reminder;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reminder: " + e.getMessage(), e);
        }
        return null;
    }
	
	public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT * FROM reminder";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setId(rs.getInt("id"));
                reminder.setBillName(rs.getString("bill_name"));
                reminder.setAmount(rs.getDouble("amount"));
                reminder.setDueDate(rs.getObject("due_date", LocalDate.class));
                reminder.setPaid(rs.getBoolean("is_paid"));
                reminders.add(reminder);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reminders: " + e.getMessage(), e);
        }
        return reminders;
    }
	
	public void deleteReminder(int id) {
        String sql = "DELETE FROM reminder WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting reminder: " + e.getMessage(), e);
        }
    }
}
