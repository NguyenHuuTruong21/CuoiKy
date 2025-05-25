package com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Category;
import com.finance.service.CategoryService;

@Controller
public class CategoryController {
	private final CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/categories")
	public String listCategories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		return "category_list";
	}
	
	@GetMapping("/category/add")
	public String showAddForm(Model model) {
		model.addAttribute("category", new Category());
		return "category_form";
	}
	
	@PostMapping("/category/add")
	public String addCategory(
			@RequestParam String name,
			@RequestParam String type,
			Model model) {
		try {
			Category category = new Category(0, name, type);
			categoryService.saveCategory(category);
			return "redirect:/categories";
		} catch (Exception e) {
			model.addAttribute("error", "Error adding category: " + e.getMessage());
            return "category_form";
		}
	}
	
	@GetMapping("/category/delete")
	public String deleteCategory(
			@RequestParam int id, Model model) {
		try {
			categoryService.deleteCategory(id);
			return "redirect:/categories";
		} catch (Exception e) {
			model.addAttribute("error", "Error deleting category: " + e.getMessage());
            return "redirect:/categories";
		}
	}
}
