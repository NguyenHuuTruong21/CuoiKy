package com.finance.entities;

public class Category {
	private int id;
    private String name;
    private String type; // "income" hoặc "expense"
    private int userId;
    
    public Category() {}

    public Category(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
