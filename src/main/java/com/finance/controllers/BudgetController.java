package com.finance.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Budget> budgets = budgetService.getAllBudgets(userId);
        Map<Integer, Double> percentUsedMap = new HashMap<>();
        for (Budget budget : budgets) {
            double percent = budgetService.getPercentUsed(userId, budget.getId());
            percentUsedMap.put(budget.getId(), percent);
        }
        model.addAttribute("budgets", budgets);
        model.addAttribute("percentUsedMap", percentUsedMap);
        return "budget_list";
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
    
    @GetMapping("/budget/add")
    public String showAddBudgetForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        // Lấy danh sách các category để hiển thị trên form
        model.addAttribute("categories", categoryService.getAllCategoriesByType(userId, "expense"));
        return "budget_form"; // Tên file jsp của form thêm ngân sách
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