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
//	public int saveReminder(Reminder reminder) {
//        String sql = "INSERT INTO reminder (bill_name, amount, due_date, is_paid) VALUES (?, ?, ?, ?)";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, reminder.getBillName());
//            stmt.setDouble(2, reminder.getAmount());
//            stmt.setObject(3, reminder.getDueDate());
//            stmt.setBoolean(4, reminder.isPaid());
//            stmt.executeUpdate();
//            ResultSet rs = stmt.getGeneratedKeys();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error saving reminder: " + e.getMessage(), e);
//        }
//        throw new RuntimeException("Failed to retrieve generated ID for reminder");
//    }
	
	public int saveReminder(Reminder reminder) {
        String sql = "INSERT INTO reminder (bill_name, amount, due_date, is_paid, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, reminder.getBillName());
            stmt.setDouble(2, reminder.getAmount());
            stmt.setObject(3, reminder.getDueDate());
            stmt.setBoolean(4, reminder.isPaid());
            stmt.setInt(5, reminder.getUserId());
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

//    public Reminder getReminderById(int id) {
//        String sql = "SELECT * FROM reminder WHERE id = ?";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Reminder reminder = new Reminder();
//                reminder.setId(rs.getInt("id"));
//                reminder.setBillName(rs.getString("bill_name"));
//                reminder.setAmount(rs.getDouble("amount"));
//                reminder.setDueDate(rs.getObject("due_date", LocalDate.class));
//                reminder.setPaid(rs.getBoolean("is_paid"));
//                return reminder;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving reminder: " + e.getMessage(), e);
//        }
//        return null;
//    }
	
	public Reminder getReminderById(int id, int userId) {
        String sql = "SELECT * FROM reminder WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setId(rs.getInt("id"));
                reminder.setBillName(rs.getString("bill_name"));
                reminder.setAmount(rs.getDouble("amount"));
                reminder.setDueDate(rs.getObject("due_date", LocalDate.class));
                reminder.setPaid(rs.getBoolean("is_paid"));
                reminder.setUserId(rs.getInt("user_id"));
                return reminder;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reminder: " + e.getMessage(), e);
        }
        return null;
    }

//    public List<Reminder> getAllReminders(int userId) {
//        List<Reminder> reminders = new ArrayList<>();
//        String sql = "SELECT * FROM reminder"; // Giả định reminder không liên kết trực tiếp với user_id
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Reminder reminder = new Reminder();
//                reminder.setId(rs.getInt("id"));
//                reminder.setBillName(rs.getString("bill_name"));
//                reminder.setAmount(rs.getDouble("amount"));
//                reminder.setDueDate(rs.getObject("due_date", LocalDate.class));
//                reminder.setPaid(rs.getBoolean("is_paid"));
//                reminders.add(reminder);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving reminders: " + e.getMessage(), e);
//        }
//        return reminders;
//    }
	
	public List<Reminder> getAllReminders(int userId) {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT * FROM reminder WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setId(rs.getInt("id"));
                reminder.setBillName(rs.getString("bill_name"));
                reminder.setAmount(rs.getDouble("amount"));
                reminder.setDueDate(rs.getObject("due_date", LocalDate.class));
                reminder.setPaid(rs.getBoolean("is_paid"));
                reminder.setUserId(rs.getInt("user_id"));
                reminders.add(reminder);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reminders: " + e.getMessage(), e);
        }
        return reminders;
    }
	
	public void updateReminder(Reminder reminder) {
        String sql = "UPDATE reminder SET bill_name = ?, amount = ?, due_date = ?, is_paid = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reminder.getBillName());
            stmt.setDouble(2, reminder.getAmount());
            stmt.setObject(3, reminder.getDueDate());
            stmt.setBoolean(4, reminder.isPaid());
            stmt.setInt(5, reminder.getId());
            stmt.setInt(6, reminder.getUserId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Reminder not found or update failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating reminder: " + e.getMessage(), e);
        }
    }

//    public void deleteReminder(int id) {
//        String sql = "DELETE FROM reminder WHERE id = ?";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error deleting reminder: " + e.getMessage(), e);
//        }
//    }
		
	public void deleteReminder(int id, int userId) {
        String sql = "DELETE FROM reminder WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting reminder: " + e.getMessage(), e);
        }
    }
}
