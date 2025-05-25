package com.finance.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
	private static final String URL = "jdbc:mysql://localhost:3306/finance_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "1234";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối Database thành công!");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy MySQL JDBC Driver!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kết nối Database: " + e.getMessage(), e);
        }
    }
}