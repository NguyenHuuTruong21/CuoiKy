package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.finance.config.DatabaseConfig;
import com.finance.entities.Category;

@Repository
public class CategoryDAO {
	public int saveCategory(Category category) {
        String sql = "INSERT INTO category (name, type, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType());
            stmt.setInt(3, category.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving category: " + e.getMessage(), e);
        }
        throw new RuntimeException("Failed to retrieve generated ID for category");
    }

//    public Category getCategoryById(int id) {
//        String sql = "SELECT * FROM category WHERE id = ?";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Category category = new Category();
//                category.setId(rs.getInt("id"));
//                category.setName(rs.getString("name"));
//                category.setType(rs.getString("type"));
//                category.setUserId(rs.getInt("user_id"));
//                return category;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving category: " + e.getMessage(), e);
//        }
//        return null;
//    }
	
	public Category getCategoryById(int id, int userId) {
        String sql = "SELECT * FROM category WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setType(rs.getString("type"));
                category.setUserId(rs.getInt("user_id"));
                return category;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving category: " + e.getMessage(), e);
        }
        return null;
    }

//    public List<Category> getAllCategories(int userId) {
//        List<Category> categories = new ArrayList<>();
//        String sql = "SELECT c.* FROM category c " +
//                     "JOIN transaction t ON t.category_id = c.id " +
//                     "WHERE t.user_id = ?";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Category category = new Category();
//                category.setId(rs.getInt("id"));
//                category.setName(rs.getString("name"));
//                category.setType(rs.getString("type"));
//                categories.add(category);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving categories: " + e.getMessage(), e);
//        }
//        return categories;
//    }
    
    public List<Category> getAllCategories(int userId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setType(rs.getString("type"));
                category.setUserId(rs.getInt("user_id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving categories: " + e.getMessage(), e);
        }
        return categories;
    }

//    public void deleteCategory(int id) {
//        String sql = "DELETE FROM category WHERE id = ?";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error deleting category: " + e.getMessage(), e);
//        }
//    }
    
    public void updateCategory(Category category) {
        String sql = "UPDATE category SET name=?, type=? WHERE id=? AND user_id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType());
            stmt.setInt(3, category.getId());
            stmt.setInt(4, category.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật danh mục: " + e.getMessage(), e);
        }
    }

    
    public void deleteCategory(int id, int userId) {
        String sql = "DELETE FROM category WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Category not found or you do not have permission to delete this category");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category: " + e.getMessage(), e);
        }
    }
}
