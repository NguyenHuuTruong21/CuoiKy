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
	
//    public Category getCategoryById(int id) {
//        return categoryDAO.getCategoryById(id);
//    }
	
	public Category getCategoryById(int id, int userId) {
        return categoryDAO.getCategoryById(id, userId);
    }

    public List<Category> getAllCategories(int userId) {
        return categoryDAO.getAllCategories(userId);
    }
	
    public List<Category> getAllCategoriesByType(int userId, String type) {
        return getAllCategories(userId).stream()
                .filter(c -> type.equalsIgnoreCase(c.getType()))
                .toList();
    }
    
    public void deleteCategory(int id, int userId) {
        Category category = getCategoryById(id, userId);
        if (category == null) {
            throw new IllegalArgumentException("Category not found or you do not have permission to delete this category");
        }
        categoryDAO.deleteCategory(id, userId);
    }
}
