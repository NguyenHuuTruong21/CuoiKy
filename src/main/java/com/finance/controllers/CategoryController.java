package com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Category;
import com.finance.entities.User;
import com.finance.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CategoryController {
	private final CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/categories")
    public String listCategories(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        model.addAttribute("categories", categoryService.getAllCategories(userId));
        return "category_list";
    }

    @GetMapping("/category/add")
    public String showAddForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("category", new Category());
        return "category_form";
    }

    @PostMapping("/category/add")
    public String addCategory(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            Model model,
            HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            Category category = new Category(0, name, type);
            category.setUserId(userId);
            categoryService.saveCategory(category);
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding category: " + e.getMessage());
            return "category_form";
        }
    }

//    @GetMapping("/category/delete")
//    public String deleteCategory(
//            @RequestParam("id") int id, Model model, HttpSession session) {
//        try {
//            User user = (User) session.getAttribute("loggedInUser");
//            if (user == null) {
//                return "redirect:/login";
//            }
//            int userId = user.getUserId();
//            categoryService.deleteCategory(id);
//            return "redirect:/categories";
//        } catch (Exception e) {
//            model.addAttribute("error", "Error deleting category: " + e.getMessage());
//            return "redirect:/categories";
//        }
//    }
    
    @GetMapping("/category/delete")
    public String deleteCategory(
            @RequestParam("id") int id, Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            categoryService.deleteCategory(id, userId);
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting category: " + e.getMessage());
            return "redirect:/categories";
        }
    }
}
