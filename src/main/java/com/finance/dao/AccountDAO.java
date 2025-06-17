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
import com.finance.entities.Account;

@Repository
public class AccountDAO {
	public int saveAccount(Account account) {
        String sql = "INSERT INTO account (name, balance, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, account.getName());
            stmt.setDouble(2, account.getBalance());
            stmt.setInt(3, account.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving account: " + e.getMessage(), e);
        }
        throw new RuntimeException("Failed to retrieve generated ID for account");
    }

//    public Account getAccountById(int id) {
//        String sql = "SELECT * FROM account WHERE id = ?";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Account account = new Account();
//                account.setId(rs.getInt("id"));
//                account.setName(rs.getString("name"));
//                account.setBalance(rs.getDouble("balance"));
//                account.setUserId(rs.getInt("user_id"));
//                return account;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving account: " + e.getMessage(), e);
//        }
//        return null;
//    }
	
	public Account getAccountById(int id, int userId) {
        String sql = "SELECT * FROM account WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setBalance(rs.getDouble("balance"));
                account.setUserId(rs.getInt("user_id"));
                return account;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving account: " + e.getMessage(), e);
        }
        return null;
    }

//    public List<Account> getAllAccounts(int userId) {
//        List<Account> accounts = new ArrayList<>();
//        String sql = "SELECT a.* FROM account a " +
//                     "JOIN transaction t ON t.account_id = a.id " +
//                     "WHERE t.user_id = ?";
//        try (Connection conn = DatabaseConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Account account = new Account();
//                account.setId(rs.getInt("id"));
//                account.setName(rs.getString("name"));
//                account.setBalance(rs.getDouble("balance"));
//                accounts.add(account);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving accounts: " + e.getMessage(), e);
//        }
//        return accounts;
//    }
    
    public List<Account> getAllAccounts(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setBalance(rs.getDouble("balance"));
                account.setUserId(rs.getInt("user_id")); // Giả định Account có phương thức setUserId
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving accounts: " + e.getMessage(), e);
        }
        return accounts;
    }
    
    public void updateAccount(Account account) {
        String sql = "UPDATE account SET name = ?, balance = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setDouble(2, account.getBalance());
            stmt.setInt(3, account.getId());
            stmt.setInt(4, account.getUserId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Account not found or you do not have permission to update this account");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account: " + e.getMessage(), e);
        }
    }

    public void deleteAccount(int id) {
        String sql = "DELETE FROM account WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting account: " + e.getMessage(), e);
        }
    }
}
