package com.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.CategoryDAO;
import com.finance.entities.Category;

@Service
public class CategoryService {
	private final CategoryDAO categoryDAO;
	
	@Autowired
	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	public int saveCategory(Category category) {
        if (category == null || category.getName() == null || category.getType() == null) {
            throw new IllegalArgumentException("Category name and type must not be null");
        }
        return categoryDAO.saveCategory(category);
    }
	
	public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
	
	public void deleteCategory(int id) {
		categoryDAO.deleteCategory(id);
	}
}
