package com.finance.entities;

public class Budget {
	private int id;
    private Category category;
    private double amount;
//    private double spent;
    private Status status;
    private int userId;
    
    public Budget() {}

	public Budget(int id, Category category, double amount) {
		this.id = id;
		this.category = category;
		this.amount = amount;
//		this.spent = 0;
		this.status = Status.ToDo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
    
}
