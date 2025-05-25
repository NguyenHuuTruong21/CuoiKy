package com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Budget;
import com.finance.service.BudgetService;
import com.finance.service.CategoryService;

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
	public String listBudgets(Model model) {
		model.addAttribute("budgets", budgetService.getAllBudgets());
		return "budget_list";
	}
	
	@GetMapping("/budget/add")
	public String showAddbudget(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("budget", new Budget());
        return "budget_form";
	}
	
	@PostMapping("/budget/add")
    public String addBudget(
            @RequestParam int categoryId,
            @RequestParam double amount,
            Model model) {
        try {
            Budget budget = new Budget(0, categoryService.getCategoryById(categoryId), amount);
            budgetService.saveBudget(budget);
            return "redirect:/budgets";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding budget: " + e.getMessage());
            return "budget_form";
        }
    }
	
	@GetMapping("/budget/delete")
    public String deleteBudget(@RequestParam int id, Model model) {
        try {
            budgetService.deleteBudget(id);
            return "redirect:/budgets";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting budget: " + e.getMessage());
            return "redirect:/budgets";
        }
    }
}
