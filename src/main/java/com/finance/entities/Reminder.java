package com.finance.entities;

import java.time.LocalDate;

public class Reminder {
	private int id;
    private String billName;
    private double amount;
    private LocalDate dueDate;
    private boolean isPaid;
    private boolean isNotified = false;
    private int userId;
    
    public Reminder() {}
    public Reminder(int id, String billName, double amount, LocalDate dueDate, boolean isPaid, boolean isNotified,  int userId) {
        this.id = id;
        this.billName = billName;
        this.amount = amount;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.isNotified = isNotified;
        this.userId = userId;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	public boolean isNotified() { 
		return isNotified; 
	}
	
	public void setNotified(boolean notified) { 
		this.isNotified = notified; 
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
