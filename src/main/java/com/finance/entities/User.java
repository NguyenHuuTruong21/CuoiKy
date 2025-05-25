package com.finance.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int userId;
    private String name;
    private String username;
    private String password;
    private String role;
    private List<Transaction> transactions;
    private List<Budget> budgets;
    
    public User() {
        this.transactions = new ArrayList<>();
        this.budgets = new ArrayList<>();
    }

	public User(int userId, String name, String username, String password, String role) {
		this.userId = userId;
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
		this.transactions = new ArrayList<>();
        this.budgets = new ArrayList<>();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Budget> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<Budget> budgets) {
		this.budgets = budgets;
	}
}
