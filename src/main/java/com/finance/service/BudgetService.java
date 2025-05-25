package com.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.BudgetDAO;
import com.finance.entities.Budget;

@Service
public class BudgetService {
	private final BudgetDAO budgetDAO;
	
	@Autowired
	public BudgetService(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}
	
	public int saveBudget(Budget budget) {
        if (budget == null || budget.getCategory() == null) {
            throw new IllegalArgumentException("Budget category must not be null");
        }
        return budgetDAO.saveBudget(budget);
    }
	
	public Budget getBudgetById(int id) {
        return budgetDAO.getBudgetById(id);
    }

    public Budget getBudgetByCategoryId(int categoryId) {
        return budgetDAO.getBudgetByCategoryId(categoryId);
    }
	
	public List<Budget> getAllBudgets() {
        return budgetDAO.getAllBudgets();
    }
	
	public void deleteBudget(int id) {
        budgetDAO.deleteBudget(id);
    }
	
	public double getRemainingBudget() {
        return getAllBudgets().stream()
                .mapToDouble(b -> b.getAmount() - b.getSpent())
                .sum();
	}
}
