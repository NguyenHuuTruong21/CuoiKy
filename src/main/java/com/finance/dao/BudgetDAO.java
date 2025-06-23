package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.stereotype.Repository;

import com.finance.config.DatabaseConfig;
import com.finance.entities.Budget;
import com.finance.entities.Category;
import com.finance.entities.Status;

@Repository
public class BudgetDAO {
	public int saveBudget(Budget budget, int userId) {
        // Kiểm tra xem category_id có thuộc về user_id không
        String checkCategorySql = "SELECT id FROM category WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkCategorySql)) {
            checkStmt.setInt(1, budget.getCategory().getId());
            checkStmt.setInt(2, userId);
            ResultSet checkRs = checkStmt.executeQuery();
            if (!checkRs.next()) {
                throw new IllegalArgumentException("Category does not belong to user");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking category ownership: " + e.getMessage(), e);
        }

        // Kiểm tra xem budget cho category_id đã tồn tại chưa
        Budget existingBudget = getBudgetByCategoryId(budget.getCategory().getId(), userId);
        if (existingBudget != null) {
            // Cập nhật budget hiện có
            String sql = "UPDATE budget SET amount = ?, status = ? WHERE id = ? AND user_id = ?";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, budget.getAmount());
                stmt.setString(2, existingBudget.getStatus().toString());
                stmt.setInt(3, existingBudget.getId());
                stmt.setInt(4, userId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RuntimeException("Budget update failed or no permission");
                }
                return existingBudget.getId();
            } catch (SQLException e) {
                throw new RuntimeException("Error updating budget: " + e.getMessage(), e);
            }
        } else {
            // Thêm mới budget
            String sql = "INSERT INTO budget (category_id, amount, status, user_id) VALUES (?, ?, ?, ?)";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, budget.getCategory().getId());
                stmt.setDouble(2, budget.getAmount());
                stmt.setString(3, budget.getStatus().toString());
                stmt.setInt(4, userId);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error saving budget: " + e.getMessage(), e);
            }
            throw new RuntimeException("Failed to retrieve generated ID for budget");
        }
    }
	
	public Budget getBudgetById(int id, int userId) {
        String sql = "SELECT b.* FROM budget b WHERE b.id = ? AND b.user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));
                budget.setAmount(rs.getDouble("amount"));
                budget.setStatus(Status.valueOf(rs.getString("status")));
                budget.setUserId(rs.getInt("user_id"));
                CategoryDAO categoryDAO = new CategoryDAO();
                budget.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id"), userId));
                return budget;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving budget: " + e.getMessage(), e);
        }
        return null;
    }
	
	public Budget getBudgetByCategoryId(int categoryId, int userId) {
        String sql = "SELECT b.* FROM budget b WHERE b.category_id = ? AND b.user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));
                budget.setAmount(rs.getDouble("amount"));
                budget.setStatus(Status.valueOf(rs.getString("status")));
                budget.setUserId(rs.getInt("user_id"));
                CategoryDAO categoryDAO = new CategoryDAO();
                budget.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id"), userId));
                return budget;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving budget by category: " + e.getMessage(), e);
        }
        return null;
    }
	
	public List<Budget> getAllBudgets(int userId) {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT b.* FROM budget b WHERE b.user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));
                budget.setAmount(rs.getDouble("amount"));
                budget.setStatus(Status.valueOf(rs.getString("status")));
                budget.setUserId(rs.getInt("user_id"));
                CategoryDAO categoryDAO = new CategoryDAO();
                budget.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id"), userId));
                budgets.add(budget);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving budgets: " + e.getMessage(), e);
        }
        return budgets;
    }
    
    public void deleteBudget(int id, int userId) {
        String sql = "DELETE FROM budget WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Budget not found or you do not have permission to delete this budget");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting budget: " + e.getMessage(), e);
        }
    }
}
