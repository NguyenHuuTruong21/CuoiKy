package com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Budget;
import com.finance.entities.Category;
import com.finance.entities.User;
import com.finance.service.BudgetService;
import com.finance.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BudgetController {
    private final BudgetService budgetService;
    private final CategoryService categoryService;

    @Autowired
    public BudgetController(BudgetService budgetService, CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }

    @GetMapping("/budgets")
    public String listBudgets(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        model.addAttribute("budgets", budgetService.getAllBudgets(userId));
        return "budget_list";
    }

    @GetMapping("/budget/add")
    public String showAddForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        model.addAttribute("categories", categoryService.getAllCategories(userId));
        model.addAttribute("budget", new Budget());
        return "budget_form";
    }

    @PostMapping("/budget/add")
    public String addBudget(
            @RequestParam("categoryId") int categoryId,
            @RequestParam("amount") double amount,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        try {
            Category category = categoryService.getCategoryById(categoryId, userId);
            if (category == null) {
                throw new IllegalArgumentException("Category not found");
            }
            Budget budget = new Budget(0, category, amount);
            budgetService.saveBudget(budget, userId);
            return "redirect:/budgets";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding budget: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories(userId));
            return "budget_form";
        }
    }

    @GetMapping("/budget/delete")
    public String deleteBudget(
            @RequestParam("id") int id,
            Model model,
            HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            budgetService.deleteBudget(id, userId);
            return "redirect:/budgets";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting budget: " + e.getMessage());
            return "redirect:/budgets";
        }
    }
}