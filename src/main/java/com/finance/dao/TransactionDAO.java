package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.finance.config.DatabaseConfig;
import com.finance.entities.Category;
import com.finance.entities.Transaction;

@Repository
public class TransactionDAO {
	public int saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (user_id, type, amount, date, category_id, description, account_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            stmt.setInt(1, Integer.parseInt(userId));
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setObject(4, transaction.getDate());
            stmt.setInt(5, transaction.getCategory() != null ? transaction.getCategory().getId() : 0);
            stmt.setString(6, transaction.getDescription());
            stmt.setInt(7, transaction.getAccount() != null ? transaction.getAccount().getId() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving transaction: " + e.getMessage(), e);
        }
        throw new RuntimeException("Failed to retrieve generated ID for transaction");
    }
	
	public Transaction getTransactionById(int id) {
        String sql = "SELECT * FROM transaction WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            stmt.setInt(1, id);
            stmt.setInt(2, Integer.parseInt(userId));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(rs.getObject("date", LocalDate.class));
                CategoryDAO categoryDAO = new CategoryDAO();
                transaction.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id")));
                AccountDAO accountDAO = new AccountDAO();
                transaction.setAccount(accountDAO.getAccountById(rs.getInt("account_id")));
                transaction.setDescription(rs.getString("description"));
                return transaction;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transaction: " + e.getMessage(), e);
        }
        return null;
    }
	
	public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            stmt.setInt(1, Integer.parseInt(userId));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(rs.getObject("date", LocalDate.class));
                CategoryDAO categoryDAO = new CategoryDAO();
                transaction.setCategory(categoryDAO.getCategoryById(rs.getInt("category_id")));
                AccountDAO accountDAO = new AccountDAO();
                transaction.setAccount(accountDAO.getAccountById(rs.getInt("account_id")));
                transaction.setDescription(rs.getString("description"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transactions: " + e.getMessage(), e);
        }
        return transactions;
    }
	
	public void deleteTransaction(int id) {
        String sql = "DELETE FROM transaction WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            stmt.setInt(1, id);
            stmt.setInt(2, Integer.parseInt(userId));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting transaction: " + e.getMessage(), e);
        }
    }
}
