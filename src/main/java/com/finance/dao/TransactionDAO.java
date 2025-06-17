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
import com.finance.entities.Account;
import com.finance.entities.Category;
import com.finance.entities.Transaction;

@Repository
public class TransactionDAO {
    public int saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (user_id, type, amount, date, category_id, description, account_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getDate().toString());
            stmt.setInt(5, transaction.getCategory().getId());
            stmt.setString(6, transaction.getDescription());
            stmt.setInt(7, transaction.getAccountId());
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

    public Transaction getTransactionById(int userId, int id) {
        String sql = "SELECT t.*, c.name AS category_name, c.type AS category_type, a.name AS account_name, a.balance AS account_balance " +
                     "FROM transaction t " +
                     "JOIN category c ON t.category_id = c.id " +
                     "JOIN account a ON t.account_id = a.id " +
                     "WHERE t.id = ? AND t.user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(LocalDate.parse(rs.getString("date")));
                
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                category.setType(rs.getString("category_type"));
                category.setUserId(userId);
                transaction.setCategory(category);

                transaction.setDescription(rs.getString("description"));
                
                Account account = new Account();
                account.setId(rs.getInt("account_id"));
                account.setName(rs.getString("account_name"));
                account.setBalance(rs.getDouble("account_balance"));
                account.setUserId(userId);
                transaction.setAccount(account);
                
                transaction.setAccountId(rs.getInt("account_id"));
                return transaction;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transaction: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Transaction> getAllTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, c.name AS category_name, c.type AS category_type, a.name AS account_name, a.balance AS account_balance " +
                     "FROM transaction t " +
                     "JOIN category c ON t.category_id = c.id " +
                     "JOIN account a ON t.account_id = a.id " +
                     "WHERE t.user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(LocalDate.parse(rs.getString("date")));
                
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                category.setType(rs.getString("category_type"));
                category.setUserId(userId);
                transaction.setCategory(category);

                transaction.setDescription(rs.getString("description"));
                
                Account account = new Account();
                account.setId(rs.getInt("account_id"));
                account.setName(rs.getString("account_name"));
                account.setBalance(rs.getDouble("account_balance"));
                account.setUserId(userId);
                transaction.setAccount(account);
                
                transaction.setAccountId(rs.getInt("account_id"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transactions: " + e.getMessage(), e);
        }
        return transactions;
    }

    public void updateTransaction(int userId, int id, String type, double amount, LocalDate date, int categoryId, String description, int accountId) {
        String sql = "UPDATE transaction SET type = ?, amount = ?, date = ?, category_id = ?, description = ?, account_id = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            stmt.setDouble(2, amount);
            stmt.setString(3, date.toString());
            stmt.setInt(4, categoryId);
            stmt.setString(5, description);
            stmt.setInt(6, accountId);
            stmt.setInt(7, id);
            stmt.setInt(8, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Transaction not found or you do not have permission to update this transaction");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating transaction: " + e.getMessage(), e);
        }
    }

    public void deleteTransaction(int userId, int id) {
        String sql = "DELETE FROM transaction WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Transaction not found or you do not have permission to delete this transaction");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting transaction: " + e.getMessage(), e);
        }
    }

    public double getTotalExpensesByAccount(int accountId, int userId) {
        String sql = "SELECT SUM(amount) FROM transaction WHERE user_id = ? AND account_id = ? AND type = 'expense'";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1) != 0 ? rs.getDouble(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total expenses: " + e.getMessage(), e);
        }
        return 0;
    }
}