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

@Repository
public class BudgetDAO {
	public int saveBudget(Budget budget) {
        String sql = "INSERT INTO budget (category_id, amount, spent) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, budget.getCategory().getId());
            stmt.setDouble(2, budget.getAmount());
            stmt.setDouble(3, budget.getSpent());
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
	
	public Budget getBudgetById(int id) {
        String sql = "SELECT * FROM budget WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));
                CategoryDAO categoryDAO = new CategoryDAO();
                budget.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id")));
                budget.setAmount(rs.getDouble("amount"));
                budget.setSpent(rs.getDouble("spent"));
                return budget;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving budget: " + e.getMessage(), e);
        }
        return null;
    }
	
	public Budget getBudgetByCategoryId(int categoryId) {
        String sql = "SELECT * FROM budget WHERE category_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));
                CategoryDAO categoryDAO = new CategoryDAO();
                budget.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id")));
                budget.setAmount(rs.getDouble("amount"));
                budget.setSpent(rs.getDouble("spent"));
                return budget;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving budget: " + e.getMessage(), e);
        }
        return null;
    }
	
	public List<Budget> getAllBudgets() {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT * FROM budget";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));
                CategoryDAO categoryDAO = new CategoryDAO();
                budget.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id")));
                budget.setAmount(rs.getDouble("amount"));
                budget.setSpent(rs.getDouble("spent"));
                budgets.add(budget);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving budgets: " + e.getMessage(), e);
        }
        return budgets;
    }
	
	public void deleteBudget(int id) {
        String sql = "DELETE FROM budget WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting budget: " + e.getMessage(), e);
        }
    }
}
