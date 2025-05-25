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
        String sql = "INSERT INTO account (name, balance) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, account.getName());
            stmt.setDouble(2, account.getBalance());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Lấy id tự động sinh
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving account: " + e.getMessage(), e);
        }
        throw new RuntimeException("Failed to retrieve generated ID for account");
    }
	
	public Account getAccountById(int id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setBalance(rs.getDouble("balance"));
                return account;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving account: " + e.getMessage(), e);
        }
        return null;
    }
	
	public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving accounts: " + e.getMessage(), e);
        }
        return accounts;
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
