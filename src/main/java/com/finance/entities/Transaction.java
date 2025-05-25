package com.finance.entities;

import java.time.LocalDate;

public class Transaction {
	private int id;
    private String type; // "income" or "expense"
    private double amount;
    private LocalDate date;
    private Category category;
    private String description;
    private Account account;
    
    public Transaction() {}
    
	
	public Transaction(int id, String type, double amount, LocalDate date, Category category, String description,
			Account account) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.category = category;
		this.description = description;
		this.account = account;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}
    
	
}